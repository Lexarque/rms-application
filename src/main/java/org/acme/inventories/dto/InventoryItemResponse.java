package org.acme.inventories.dto;

import org.acme.inventories.model.InventoryItem;

import java.time.OffsetDateTime;

public record InventoryItemResponse(
        Long id,
        String sku,
        String name,
        String category,
        String unit,
        Integer qtyOnHand,
        Integer reorderLevel,
        String status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        boolean lowStock
) {
    public static InventoryItemResponse from(InventoryItem item) {
        return new InventoryItemResponse(
                item.id,
                item.sku,
                item.name,
                item.category,
                item.unit,
                item.qtyOnHand,
                item.reorderLevel,
                item.status.name(),
                item.createdAt,
                item.updatedAt,
                item.qtyOnHand <= item.reorderLevel
        );
    }
}
