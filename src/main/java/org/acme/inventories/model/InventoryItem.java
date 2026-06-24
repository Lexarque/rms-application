package org.acme.inventories.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.acme.shared.model.BaseEntity;

@Entity
@Table(name = "inventory")
@AttributeOverride(name = "updatedAt", column = @Column(name = "last_updated", nullable = false))
public class InventoryItem extends BaseEntity {

    @Column(name = "item_name", nullable = false, length = 255)
    public String itemName;

    @Column(nullable = false)
    public int quantity;

    @Column(name = "minimum_threshold", nullable = false)
    public int minimumThreshold;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    public InventoryUnit unit;
}
