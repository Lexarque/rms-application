package org.acme.order.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.acme.itemMenu.model.MenuItem;
import org.acme.itemMenu.repository.MenuItemRepository;
import org.acme.order.dto.*;
import org.acme.order.model.*;
import org.acme.order.repository.OrderItemRepository;
import org.acme.order.repository.OrderRepository;
import org.acme.reservation.model.Reservation;
import org.acme.reservation.model.ReservationStatus;
import org.acme.reservation.repository.ReservationRepository;
import org.acme.restaurantTable.model.RestaurantTable;
import org.acme.restaurantTable.repository.RestaurantTableRepository;
import org.acme.user.model.User;
import org.acme.user.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderItemRepository orderItemRepository;

    @Inject
    RestaurantTableRepository tableRepository;

    @Inject
    ReservationRepository reservationRepository;

    @Inject
    MenuItemRepository menuItemRepository;

    @Inject
    UserRepository userRepository;

    // ---------- Queries ----------

    public List<Order> listOrders(String search, OrderStatus status, OrderType type,
                                  LocalDate fromDate, LocalDate toDate,
                                  int page, int size, String sort) {
        int safePage = Math.max(page, 0);
        int safeSize = size <= 0 ? 20 : Math.min(size, 200);

        return orderRepository.search(
                normalize(search), status, type, fromDate, toDate, safePage, safeSize, sort
        );
    }

    public Order getOrder(UUID id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }
        return order;
    }

    // ---------- Create ----------

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        RestaurantTable table = tableRepository.findById(request.tableId());
        if (table == null) {
            throw new NotFoundException("Table not found");
        }
        if (!Boolean.TRUE.equals(table.getIsActive())) {
            throw new BadRequestException("Table is not active");
        }

        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setType(request.type());
        order.setStatus(OrderStatus.DRAFT);
        order.setTable(table);
        order.setSpecialRequests(normalize(request.specialRequests()));

        if (request.type() == OrderType.RESERVATION) {
            if (request.reservationId() != null) {
                // Use existing reservation (staff-created flow)
                Reservation reservation = reservationRepository.findById(request.reservationId());
                if (reservation == null) {
                    throw new NotFoundException("Reservation not found");
                }
                order.setReservation(reservation);
                order.setCustomer(reservation.getCustomer());
            } else if (request.customerId() != null) {
                // Auto-create reservation for customer self-service flow
                User customer = userRepository.findById(request.customerId());
                if (customer == null) {
                    throw new NotFoundException("Customer not found");
                }
                Reservation reservation = new Reservation();
                reservation.setCustomer(customer);
                reservation.setTable(table);
                reservation.setReservationTime(LocalDateTime.now());
                reservation.setPartySize(1);
                reservation.setStatus(ReservationStatus.SEATED);
                reservation.setSpecialRequests(normalize(request.specialRequests()));
                reservationRepository.persist(reservation);

                order.setReservation(reservation);
                order.setCustomer(customer);
            } else {
                throw new BadRequestException("customerId is required for customer RESERVATION orders");
            }
        }

        if (request.customerId() != null && order.getCustomer() == null) {
            User customer = userRepository.findById(request.customerId());
            if (customer == null) {
                throw new NotFoundException("Customer not found");
            }
            order.setCustomer(customer);
        }

        if (request.staffId() != null) {
            User staff = userRepository.findById(request.staffId());
            if (staff == null) {
                throw new NotFoundException("Staff not found");
            }
            order.setStaff(staff);
        }

        orderRepository.persist(order);

        if (request.items() != null && !request.items().isEmpty()) {
            attachItems(order, request.items());
        }

        recalculateTotal(order);

        return order;
    }

    // ---------- Update ----------

    @Transactional
    public Order updateOrder(UUID id, UpdateOrderRequest request) {
        Order order = getOrder(id);

        if (order.getStatus() != OrderStatus.DRAFT && order.getStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Order can only be edited while in DRAFT or PENDING status");
        }

        if (request.tableId() != null) {
            RestaurantTable table = tableRepository.findById(request.tableId());
            if (table == null) {
                throw new NotFoundException("Table not found");
            }
            order.setTable(table);
        }

        if (request.specialRequests() != null) {
            order.setSpecialRequests(normalize(request.specialRequests()));
        }

        if (request.staffId() != null) {
            User staff = userRepository.findById(request.staffId());
            if (staff == null) {
                throw new NotFoundException("Staff not found");
            }
            order.setStaff(staff);
        }

        if (request.items() != null) {
            orderItemRepository.deleteByOrder(order.getId());
            attachItems(order, request.items());
        }

        recalculateTotal(order);

        return order;
    }

    // ---------- Cancel ----------

    @Transactional
    public void cancelOrder(UUID id) {
        Order order = getOrder(id);

        if (order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new BadRequestException("Order is already " + order.getStatus().name().toLowerCase(Locale.ROOT));
        }

        order.setStatus(OrderStatus.CANCELLED);

        if (order.getReservation() != null) {
            order.getReservation().setStatus(ReservationStatus.CANCELLED);
        }
    }

    // ---------- Status transitions ----------

    @Transactional
    public Order changeStatus(UUID id, StatusChangeRequest request) {
        Order order = getOrder(id);
        OrderStatus current = order.getStatus();
        OrderStatus next = request.status();

        if (!isValidTransition(current, next)) {
            throw new BadRequestException("Cannot transition from " + current + " to " + next);
        }

        order.setStatus(next);

        if (next == OrderStatus.CONFIRMED && order.getReservation() != null) {
            order.getReservation().setStatus(ReservationStatus.SEATED);
        }

        return order;
    }

    // ---------- Helpers ----------

    private void attachItems(Order order, List<CreateOrderItemRequest> items) {
        for (CreateOrderItemRequest itemRequest : items) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.menuItemId());
            if (menuItem == null) {
                throw new NotFoundException("Menu item not found: " + itemRequest.menuItemId());
            }
            if (itemRequest.quantity() == null || itemRequest.quantity() <= 0) {
                throw new BadRequestException("quantity must be greater than 0");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemRequest.quantity());
            orderItem.setPriceAtOrder(menuItem.getPrice());
            orderItem.setCustomInstructions(normalize(itemRequest.customInstructions()));

            orderItemRepository.persist(orderItem);
        }
    }

    private void recalculateTotal(Order order) {
        List<OrderItem> items = orderItemRepository.find("order.id", order.getId()).list();

        BigDecimal total = items.stream()
                .map(item -> item.getPriceAtOrder().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);
    }

    private boolean isValidTransition(OrderStatus current, OrderStatus next) {
        return switch (current) {
            case DRAFT -> next == OrderStatus.PENDING || next == OrderStatus.CANCELLED;
            case PENDING -> next == OrderStatus.CONFIRMED || next == OrderStatus.CANCELLED;
            case CONFIRMED -> next == OrderStatus.PREPARING || next == OrderStatus.CANCELLED;
            case PREPARING -> next == OrderStatus.READY || next == OrderStatus.CANCELLED;
            case READY -> next == OrderStatus.SERVED;
            case SERVED -> next == OrderStatus.COMPLETED;
            case COMPLETED, CANCELLED -> false;
        };
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }

    private String normalize(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}