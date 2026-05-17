package org.acme.itemMenu.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.itemMenu.dto.MenuIngredientResponse;
import org.acme.itemMenu.dto.MenuItemResponse;
import org.acme.itemMenu.model.MenuIngredient;
import org.acme.itemMenu.model.MenuItem;

@ApplicationScoped
public class MenuMapper {

    public MenuItemResponse toItemResponse(MenuItem item) {
        if (item == null) return null;

        return new MenuItemResponse(
                item.id,
                item.itemName,
                item.description,
                item.price,
                item.imageUrl,
                Boolean.TRUE.equals(item.isAvailable),
                item.lastUpdated
        );
    }

    public MenuIngredientResponse toIngredientResponse(MenuIngredient ing) {
        if (ing == null) return null;

        return new MenuIngredientResponse(
                ing.id,
                ing.inventoryItem.id,
                ing.inventoryItem.itemName,
                ing.quantityRequired
        );
    }
}