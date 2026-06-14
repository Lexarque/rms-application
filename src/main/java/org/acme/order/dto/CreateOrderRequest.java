package org.acme.order.dto;

import org.acme.order.model.OrderType;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        OrderType type,
        UUID tableId,
        UUID reservationId,
        UUID customerId,
        UUID staffId,
        String specialRequests,
        List<CreateOrderItemRequest> items
) {
}