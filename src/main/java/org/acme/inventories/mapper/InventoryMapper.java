package org.acme.inventories.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.inventories.dto.InventoryItemResponse;
import org.acme.inventories.dto.InventoryMovementResponse;
import org.acme.inventories.model.InventoryItem;
import org.acme.inventories.model.InventoryMovement;
import org.acme.inventories.model.StockStatus;

@ApplicationScoped
public class InventoryMapper {

    public InventoryItemResponse toItemResponse(InventoryItem item) {
        return new InventoryItemResponse(
                item.id,
                item.itemName,
                item.quantity,
                item.minimumThreshold,
                computeStatus(item.quantity, item.minimumThreshold),
                item.createdAt,
                item.lastUpdated
        );
    }

    public InventoryMovementResponse toMovementResponse(InventoryMovement movement) {
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

    public StockStatus computeStatus(int quantity, int minimumThreshold) {
        if (quantity <= 0) {
            return StockStatus.OUT;
        }
        if (quantity <= minimumThreshold) {
            return StockStatus.LOW;
        }
        return StockStatus.AVAILABLE;
    }
}
