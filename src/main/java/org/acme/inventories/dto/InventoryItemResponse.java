package org.acme.inventories.dto;

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
}
