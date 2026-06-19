package org.acme.itemMenu.dto;

import java.util.UUID;

import org.acme.inventories.model.InventoryItem;
import org.acme.itemMenu.model.MenuIngredient;

public record MenuIngredientResponse(
        UUID id,
        UUID inventoryId,
        String inventoryItemName,
        Integer quantityRequired,
        Integer availableQuantity,
        boolean sufficient
) {
    public static MenuIngredientResponse from(MenuIngredient ingredient) {

        InventoryItem inv = ingredient.getInventoryItem();

        int available = inv.getQuantity();
        int required = ingredient.getQuantityRequired();

        return new MenuIngredientResponse(
                ingredient.getId(),
                inv.getId(),
                inv.getItemName(),
                required,
                available,
                available >= required
        );
        }
}