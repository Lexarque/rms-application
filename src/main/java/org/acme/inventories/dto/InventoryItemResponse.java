package org.acme.inventories.dto;

import org.acme.inventories.model.InventoryItem;
import org.acme.inventories.model.StockStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryItemResponse(
        UUID id,
        String itemName,
        int quantity,
        int minimumThreshold,
        StockStatus status,
        LocalDateTime createdAt,
        LocalDateTime lastUpdated
) {
    public static InventoryItemResponse from(InventoryItem item) {
        return new InventoryItemResponse(
                item.id,
                item.itemName,
                item.quantity,
                item.minimumThreshold,
                computeStatus(item.quantity, item.minimumThreshold),
                item.createdAt,
                item.updatedAt
        );
    }

    public static StockStatus computeStatus(int quantity, int minimumThreshold) {
        if (quantity <= 0) {
            return StockStatus.OUT;
        }
        if (quantity <= minimumThreshold) {
            return StockStatus.LOW;
        }
        return StockStatus.AVAILABLE;
    }
}
