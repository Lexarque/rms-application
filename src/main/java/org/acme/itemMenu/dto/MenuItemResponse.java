package org.acme.itemMenu.dto;

import org.acme.itemMenu.model.MenuCategory;
import org.acme.itemMenu.model.MenuItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record MenuItemResponse(
        UUID id,
        String itemName,
        String description,
        BigDecimal price,
        MenuCategory category,
        String imageUrl,
        Boolean isAvailable,
        boolean stockAvailable,
        List<MenuIngredientResponse> ingredients
) {
    public static MenuItemResponse from(MenuItem item) {
        List<MenuIngredientResponse> ingredientResponses = item.getIngredients()
                .stream()
                .map(MenuIngredientResponse::from)
                .toList();

        boolean stockAvailable = ingredientResponses.isEmpty()
                || ingredientResponses.stream().allMatch(MenuIngredientResponse::sufficient);

        return new MenuItemResponse(
                item.getId(),
                item.getItemName(),
                item.getDescription(),
                item.getPrice(),
                item.getCategory(),
                item.getImageUrl(),
                item.getAvailable(),
                stockAvailable,
                ingredientResponses
        );
    }
}