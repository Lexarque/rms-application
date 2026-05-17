package org.acme.itemMenu.dto;

import java.math.BigDecimal;

public record CreateMenuItemRequest(
        String itemName,
        String description,
        BigDecimal price,
        String imageUrl,
        Boolean isAvailable
) {
}