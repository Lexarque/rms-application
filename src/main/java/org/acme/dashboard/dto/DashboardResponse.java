package org.acme.dashboard.dto;

import java.math.BigDecimal;
import java.util.Map;

public record DashboardResponse(
        StaffStats staff,
        OrderStats orders,
        MenuStats menu,
        InventoryStats inventory
) {
    public record StaffStats(
            long total,
            Map<String, Long> byRole
    ) {}

    public record OrderStats(
            long todayCount,
            BigDecimal todayRevenue,
            long activeCount,
            Map<String, Long> byStatus
    ) {}

    public record MenuStats(
            long total,
            long availableCount,
            long outOfStockCount
    ) {}

    public record InventoryStats(
            long total,
            long lowStockCount
    ) {}
}