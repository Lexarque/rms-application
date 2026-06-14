package org.acme.order.dto;

import org.acme.order.model.Order;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID id,
                            String orderNumber,
                            String type,
                            String status,
                            UUID tableId,
                            String tableNumber,
                            UUID reservationId,
                            UUID customerId,
                            String customerName,
                            UUID staffId,
                            String staffName,
                            String specialRequests,
                            BigDecimal totalAmount,
                            List<OrderItemResponse> items,
                            ZonedDateTime createdAt,
                            ZonedDateTime updatedAt) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getType().name(),
                order.getStatus().name(),
                order.getTable().getId(),
                order.getTable().getTableNumber(),
                order.getReservation() != null ? order.getReservation().getId() : null,
                order.getCustomer() != null ? order.getCustomer().getId() : null,
                order.getCustomer() != null ? order.getCustomer().getFullName() : null,
                order.getStaff() != null ? order.getStaff().getId() : null,
                order.getStaff() != null ? order.getStaff().getFullName() : null,
                order.getSpecialRequests(),
                order.getTotalAmount(),
                order.getItems().stream().map(OrderItemResponse::from).toList(),
                order.getCreatedAt().atZone(ZoneId.systemDefault()),
                order.getUpdatedAt().atZone(ZoneId.systemDefault())
        );
    }
}