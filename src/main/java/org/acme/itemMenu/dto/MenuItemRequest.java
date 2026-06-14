package org.acme.itemMenu.dto;

import org.acme.itemMenu.model.MenuCategory;
import java.math.BigDecimal;

public record MenuItemRequest(
        String itemName,
        String description,
        BigDecimal price,
        MenuCategory category,
        String imageUrl,
        Boolean isAvailable
) {}