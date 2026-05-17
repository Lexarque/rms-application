package org.acme.itemMenu.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MenuItemResponse(
        UUID id,
        String item_name,
        String description,
        BigDecimal price,
        String image_url,
        boolean is_available,
        LocalDateTime last_updated
) {}