package org.acme.inventories.dto;

import org.acme.inventories.model.InventoryMovement;
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
    public static InventoryMovementResponse from(InventoryMovement movement) {
        return new InventoryMovementResponse(
                movement.id,
                movement.item.id,
                movement.movementType,
                movement.quantity,
                movement.reference,
                movement.note,
                movement.performedBy,
                movement.createdAt
        );
    }
}
