package org.acme.payment.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.payment.model.Payment;
import org.acme.shared.repository.BaseRepository;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PaymentRepository extends BaseRepository<Payment> {

    public Optional<Payment> findByOrderId(UUID orderId) {
        return find("order.id", orderId).firstResultOptional();
    }
}