package org.acme.order.dto;

import org.acme.order.model.OrderItem;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(
        UUID id,
        UUID menuItemId,
        String menuItemName,
        Integer quantity,
        BigDecimal priceAtOrder,
        String customInstructions
) {
    public static OrderItemResponse from(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getMenuItem().getId(),
                item.getMenuItem().getItemName(),
                item.getQuantity(),
                item.getPriceAtOrder(),
                item.getCustomInstructions()
        );
    }
}