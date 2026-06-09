package org.acme.inventories.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.acme.shared.model.BaseEntity;

@Entity
@Table(name = "inventory_movements")
public class InventoryMovement extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    public InventoryItem item;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false, length = 20)
    public MovementType movementType;

    @Column(nullable = false)
    public int quantity;

    @Column(length = 150)
    public String reference;

    @Column(length = 500)
    public String note;

    @Column(name = "performed_by", length = 150)
    public String performedBy;
}
