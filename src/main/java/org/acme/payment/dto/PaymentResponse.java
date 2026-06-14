package org.acme.payment.dto;

import org.acme.payment.model.Payment;
import org.acme.payment.model.PaymentMethod;
import org.acme.payment.model.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID orderId,
        String orderNumber,
        BigDecimal amount,
        PaymentMethod method,
        PaymentStatus status,
        BigDecimal cashTendered,
        BigDecimal changeAmount
) {
    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getOrder().getOrderNumber(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getCashTendered(),
                payment.getChangeAmount()
        );
    }
}