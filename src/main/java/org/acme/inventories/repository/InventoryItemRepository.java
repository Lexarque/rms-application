package org.acme.inventories.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.inventories.model.InventoryItem;

import java.util.Optional;

@ApplicationScoped
public class InventoryItemRepository implements PanacheRepository<InventoryItem> {

    public Optional<InventoryItem> findBySku(String sku) {
        return find("lower(sku) = ?1", sku.toLowerCase()).firstResultOptional();
    }
}
