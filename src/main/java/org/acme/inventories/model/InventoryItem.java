package org.acme.inventories.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "sku", nullable = false, length = 50, unique = true)
    public String sku;

    @Column(name = "name", nullable = false, length = 150)
    public String name;

    @Column(name = "category", nullable = false, length = 80)
    public String category;

    @Column(name = "unit", nullable = false, length = 20)
    public String unit;

    @Column(name = "qty_on_hand", nullable = false)
    public Integer qtyOnHand = 0;

    @Column(name = "reorder_level", nullable = false)
    public Integer reorderLevel = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    public InventoryStatus status = InventoryStatus.ACTIVE;

    @Column(name = "created_at", nullable = false)
    public OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    public OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
