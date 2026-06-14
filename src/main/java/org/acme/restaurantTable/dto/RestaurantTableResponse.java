package org.acme.restaurantTable.dto;

import org.acme.restaurantTable.model.RestaurantTable;

import java.util.UUID;

public record RestaurantTableResponse(
        UUID id,
        String tableNumber,
        Integer capacity,
        Boolean isActive
) {
    public static RestaurantTableResponse from(RestaurantTable table) {
        return new RestaurantTableResponse(
                table.getId(),
                table.getTableNumber(),
                table.getCapacity(),
                table.getIsActive()
        );
    }
}