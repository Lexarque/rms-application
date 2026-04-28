package org.acme.inventories.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.inventories.model.InventoryTransaction;

@ApplicationScoped
public class InventoryTransactionRepository implements PanacheRepository<InventoryTransaction> {
}
