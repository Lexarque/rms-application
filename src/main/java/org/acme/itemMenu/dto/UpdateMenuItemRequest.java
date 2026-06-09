package org.acme.itemMenu.dto;

import java.math.BigDecimal;

public record UpdateMenuItemRequest(
        String itemName,
        String description,
        BigDecimal price,
        String imageUrl,
        Boolean isAvailable
) {
}