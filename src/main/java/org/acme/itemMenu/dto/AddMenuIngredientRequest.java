package org.acme.itemMenu.dto;

import java.util.UUID;

public record AddMenuIngredientRequest(
        UUID inventoryItemId,
        Integer quantityRequired
) {
}