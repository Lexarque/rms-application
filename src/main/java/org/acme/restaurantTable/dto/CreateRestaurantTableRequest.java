package org.acme.restaurantTable.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRestaurantTableRequest(
        @NotBlank String tableNumber,
        @NotNull @Min(1) Integer capacity,
        Boolean isActive
) {}