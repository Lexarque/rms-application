package org.acme.payment.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.acme.order.model.Order;
import org.acme.order.model.OrderStatus;
import org.acme.order.repository.OrderRepository;
import org.acme.payment.dto.ProcessPaymentRequest;
import org.acme.payment.model.Payment;
import org.acme.payment.model.PaymentMethod;
import org.acme.payment.model.PaymentStatus;
import org.acme.payment.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.UUID;

@ApplicationScoped
public class PaymentService {

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    OrderRepository orderRepository;

    public Payment getPaymentByOrder(UUID orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("Payment not found for order"));
    }

    @Transactional
    public Payment processPayment(UUID orderId, ProcessPaymentRequest request) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BadRequestException("Cannot pay for a cancelled order");
        }

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new BadRequestException("Order is already paid");
        }

        if (order.getTotalAmount() == null || order.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Order has no amount to pay");
        }

        paymentRepository.findByOrderId(orderId).ifPresent(existing -> {
            if (existing.getStatus() == PaymentStatus.PAID) {
                throw new BadRequestException("Order is already paid");
            }
            paymentRepository.delete(existing);
        });

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setMethod(request.method());

        if (request.method() == PaymentMethod.CASH) {
            if (request.cashTendered() == null) {
                throw new BadRequestException("cashTendered is required for CASH payments");
            }
            if (request.cashTendered().compareTo(order.getTotalAmount()) < 0) {
                throw new BadRequestException("Cash tendered is less than the total amount");
            }
            BigDecimal change = request.cashTendered().subtract(order.getTotalAmount());
            payment.setCashTendered(request.cashTendered());
            payment.setChangeAmount(change);
        }

        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.persist(payment);

        order.setStatus(OrderStatus.COMPLETED);

        return payment;
    }
}