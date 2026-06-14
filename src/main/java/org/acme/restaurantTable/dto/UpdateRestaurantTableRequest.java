package org.acme.restaurantTable.dto;

import jakarta.validation.constraints.Min;

public record UpdateRestaurantTableRequest(
        String tableNumber,
        @Min(1) Integer capacity,
        Boolean isActive
) {}