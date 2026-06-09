package org.acme.itemMenu.dto;

import java.util.UUID;

public record MenuIngredientResponse(
        UUID id,
        UUID inventory_item_id,
        String inventory_item_name,
        Integer quantity_required
) {}