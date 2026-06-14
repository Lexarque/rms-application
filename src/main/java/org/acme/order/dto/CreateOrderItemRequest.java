package org.acme.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreateOrderItemRequest(
        @NotNull UUID menuItemId,
        @NotNull @Positive Integer quantity,
        String customInstructions
) {
}