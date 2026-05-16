package org.acme.inventories.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.inventories.model.InventoryMovement;

import java.util.UUID;

@ApplicationScoped
public class InventoryMovementRepository implements PanacheRepositoryBase<InventoryMovement, UUID> {
}
