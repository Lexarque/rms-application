package org.acme.itemMenu.dto;

import java.util.UUID;

public record MenuIngredientResponse(
        UUID id,
        UUID inventoryId,
        String inventoryItemName,
        Integer quantityRequired,
        Integer availableQuantity,
        boolean sufficient
) {
    public static MenuIngredientResponse from(
            org.acme.itemMenu.model.MenuIngredient ingredient) {
        int available = ingredient.getInventoryItem().quantity;
        int required = ingredient.getQuantityRequired();
        return new MenuIngredientResponse(
                ingredient.getId(),
                ingredient.getInventoryItem().id,
                ingredient.getInventoryItem().itemName,
                required,
                available,
                available >= required
        );
    }
}