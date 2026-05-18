package org.acme.itemMenu.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MenuItemResponse(
        UUID id,
        String itemName,
        String description,
        BigDecimal price,
        String imageUrl,
        boolean isAvailable,
        LocalDateTime lastUpdated
) {}