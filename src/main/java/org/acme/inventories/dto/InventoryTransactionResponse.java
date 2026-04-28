package org.acme.inventories.dto;

import org.acme.inventories.model.InventoryTransaction;

import java.time.OffsetDateTime;

public record InventoryTransactionResponse(
        Long id,
        Long itemId,
        String txnType,
        Integer quantity,
        String note,
        String refNo,
        String createdBy,
        OffsetDateTime createdAt
) {
    public static InventoryTransactionResponse from(InventoryTransaction txn) {
        return new InventoryTransactionResponse(
                txn.id,
                txn.item.id,
                txn.txnType.name(),
                txn.quantity,
                txn.note,
                txn.refNo,
                txn.createdBy,
                txn.createdAt
        );
    }
}
