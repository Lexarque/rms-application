package org.acme.inventories.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.acme.inventories.model.MovementType;

public record AdjustQuantityRequest(
        @NotNull(message = "movementType is required")
        MovementType movementType,

        @NotNull(message = "quantity is required")
        @Min(value = 1, message = "quantity must be greater than 0")
        Integer quantity,

        @Size(max = 500)
        String note,

        @Size(max = 150)
        String reference,

        @Size(max = 150)
        String performedBy
) {
}
