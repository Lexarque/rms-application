package org.acme.order.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.order.model.OrderItem;
import org.acme.shared.repository.BaseRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderItemRepository extends BaseRepository<OrderItem> {

    public List<OrderItem> findByOrder(UUID orderId) {
        return find("order.id", orderId).list();
    }

    public long deleteByOrder(UUID orderId) {
        return delete("order.id", orderId);
    }
}