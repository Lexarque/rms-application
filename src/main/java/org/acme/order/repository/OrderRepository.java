package org.acme.order.repository;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.order.model.Order;
import org.acme.order.model.OrderStatus;
import org.acme.order.model.OrderType;
import org.acme.shared.repository.BaseRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrderRepository extends BaseRepository<Order> {

    public Optional<Order> findByOrderNumber(String orderNumber) {
        return find("orderNumber", orderNumber).firstResultOptional();
    }

    public List<Order> search(String search,
                              OrderStatus status,
                              OrderType type,
                              LocalDate fromDate,
                              LocalDate toDate,
                              int page,
                              int size,
                              String sort) {

        StringBuilder query = new StringBuilder("1=1");
        var params = new io.quarkus.panache.common.Parameters();

        if (search != null && !search.isBlank()) {
            query.append(" and (lower(orderNumber) like :search or lower(specialRequests) like :search)");
            params.and("search", "%" + search.toLowerCase() + "%");
        }

        if (status != null) {
            query.append(" and status = :status");
            params.and("status", status);
        }

        if (type != null) {
            query.append(" and type = :type");
            params.and("type", type);
        }

        if (fromDate != null) {
            query.append(" and createdAt >= :fromDate");
            params.and("fromDate", fromDate.atStartOfDay());
        }

        if (toDate != null) {
            query.append(" and createdAt < :toDate");
            params.and("toDate", toDate.plusDays(1).atStartOfDay());
        }

        Sort sortObj = parseSort(sort);

        return find(query.toString(), sortObj, params)
                .page(Page.of(page, size))
                .list();
    }

    public List<Order> findByCustomer(UUID customerId) {
        return find("customer.id", Sort.by("createdAt").descending(), customerId).list();
    }

    public List<Order> findByStaff(UUID staffId) {
        return find("staff.id", Sort.by("createdAt").descending(), staffId).list();
    }

    public List<Order> findByTable(UUID tableId) {
        return find("table.id", Sort.by("createdAt").descending(), tableId).list();
    }

    public List<Order> findByReservation(UUID reservationId) {
        return find("reservation.id", reservationId).list();
    }

    public List<Order> findActiveByTable(UUID tableId) {
        return find("table.id = :tableId and status not in (:closedStatuses)",
                io.quarkus.panache.common.Parameters
                        .with("tableId", tableId)
                        .and("closedStatuses", List.of(OrderStatus.COMPLETED, OrderStatus.CANCELLED)))
                .list();
    }

    public List<Order> findByDateRange(LocalDateTime from, LocalDateTime to) {
        return find("createdAt >= ?1 and createdAt < ?2", Sort.by("createdAt"), from, to).list();
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by("createdAt").descending();
        }

        String[] parts = sort.split(",");
        String field = toCamelCase(parts[0]);
        boolean descending = parts.length > 1 && parts[1].equalsIgnoreCase("desc");

        return descending ? Sort.by(field).descending() : Sort.by(field).ascending();
    }

    private String toCamelCase(String snake) {
        String[] words = snake.split("_");
        StringBuilder sb = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            sb.append(words[i].substring(0, 1).toUpperCase()).append(words[i].substring(1));
        }
        return sb.toString();
    }
}