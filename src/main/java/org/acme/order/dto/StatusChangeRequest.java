package org.acme.order.dto;

import org.acme.order.model.OrderStatus;

import java.util.UUID;

public record StatusChangeRequest(
        OrderStatus status,
        UUID staffId
) {
}