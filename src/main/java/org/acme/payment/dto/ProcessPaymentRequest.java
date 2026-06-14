package org.acme.payment.dto;

import org.acme.payment.model.PaymentMethod;
import java.math.BigDecimal;

public record ProcessPaymentRequest(
        PaymentMethod method,
        BigDecimal cashTendered  // only required for CASH
) {}