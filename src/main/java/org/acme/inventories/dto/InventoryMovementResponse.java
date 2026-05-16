package org.acme.inventories.dto;

import org.acme.inventories.model.MovementType;

import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryMovementResponse(
        UUID id,
        UUID itemId,
        MovementType movementType,
        int quantity,
        String reference,
        String note,
        String performedBy,
        LocalDateTime createdAt
) {
}
