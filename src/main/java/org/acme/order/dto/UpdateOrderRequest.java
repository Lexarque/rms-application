package org.acme.order.dto;

import java.util.List;
import java.util.UUID;

public record UpdateOrderRequest(
        UUID tableId,
        String specialRequests,
        UUID staffId,
        List<CreateOrderItemRequest> items
) {
}