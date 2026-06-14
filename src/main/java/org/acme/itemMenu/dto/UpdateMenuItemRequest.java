package org.acme.itemMenu.dto;

import org.acme.itemMenu.model.MenuCategory;

public record UpdateMenuItemRequest(
        String itemName,
        String description,
        Double price,
        MenuCategory category,
        String imageUrl,
        Boolean isAvailable
) {}