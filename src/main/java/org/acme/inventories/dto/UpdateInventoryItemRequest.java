package org.acme.inventories.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateInventoryItemRequest(
        @NotBlank(message = "itemName is required")
        @Size(max = 255)
        String itemName,

        @Min(value = 0, message = "quantity cannot be negative")
        Integer quantity,

        @Min(value = 0, message = "minimumThreshold cannot be negative")
        Integer minimumThreshold
) {
}
