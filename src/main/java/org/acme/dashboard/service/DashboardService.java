package org.acme.dashboard.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dashboard.dto.DashboardResponse;
import org.acme.inventories.repository.InventoryItemRepository;
import org.acme.itemMenu.model.MenuItem;
import org.acme.itemMenu.repository.MenuItemRepository;
import org.acme.order.model.Order;
import org.acme.order.model.OrderStatus;
import org.acme.order.repository.OrderRepository;
import org.acme.user.model.User;
import org.acme.user.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class DashboardService {

    @Inject
    UserRepository userRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    MenuItemRepository menuItemRepository;

    @Inject
    InventoryItemRepository inventoryItemRepository;

    public DashboardResponse getDashboard(String callerRole) {
        return new DashboardResponse(
                buildStaffStats(callerRole),
                buildOrderStats(),
                buildMenuStats(),
                buildInventoryStats()
        );
    }

    private DashboardResponse.StaffStats buildStaffStats(String callerRole) {
        if ("staff".equals(callerRole)) {
            return null;
        }
        System.out.println("Fetching staff stats for role: " + callerRole);
        List<User> users = userRepository.listAll();

        // Manager can only see staff + customer counts; admin sees everything
        Set<String> visibleRoles = "admin".equals(callerRole)
                ? null
                : Set.of("staff", "customer");

        List<User> visibleUsers = visibleRoles == null
                ? users
                : users.stream().filter(u -> visibleRoles.contains(u.role)).toList();

        Map<String, Long> byRole = visibleUsers.stream()
                .collect(Collectors.groupingBy(u -> u.role, Collectors.counting()));

        return new DashboardResponse.StaffStats(visibleUsers.size(), byRole);
    }

    private DashboardResponse.OrderStats buildOrderStats() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Order> todayOrders = orderRepository.find(
                "createdAt >= ?1 and createdAt < ?2", startOfDay, endOfDay
        ).list();

        BigDecimal todayRevenue = todayOrders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .map(Order::getTotalAmount)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long activeCount = orderRepository.find(
                "status not in (?1, ?2)", OrderStatus.COMPLETED, OrderStatus.CANCELLED
        ).count();

        List<Order> allOrders = orderRepository.listAll();
        Map<String, Long> byStatus = allOrders.stream()
                .collect(Collectors.groupingBy(o -> o.getStatus().name(), Collectors.counting()));

        return new DashboardResponse.OrderStats(
                todayOrders.size(), todayRevenue, activeCount, byStatus
        );
    }

    private DashboardResponse.MenuStats buildMenuStats() {
        List<MenuItem> items = menuItemRepository.listAll();

        long availableCount = items.stream()
                .filter(MenuItem::getAvailable)
                .count();

        long outOfStockCount = items.stream()
                .filter(this::isOutOfStock)
                .count();

        return new DashboardResponse.MenuStats(items.size(), availableCount, outOfStockCount);
    }

    private boolean isOutOfStock(MenuItem item) {
        if (item.getIngredients() == null || item.getIngredients().isEmpty()) {
            return false;
        }
        return item.getIngredients().stream()
                .anyMatch(ing -> ing.getInventoryItem().quantity < ing.getQuantityRequired());
    }

    private DashboardResponse.InventoryStats buildInventoryStats() {
        var items = inventoryItemRepository.listAll();

        long lowStockCount = items.stream()
                .filter(i -> i.quantity <= i.minimumThreshold)
                .count();

        return new DashboardResponse.InventoryStats(items.size(), lowStockCount);
    }
}