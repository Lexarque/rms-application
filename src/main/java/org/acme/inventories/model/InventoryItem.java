package org.acme.inventories.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inventory")
public class InventoryItem {

    @Id
    public UUID id;

    @Column(name = "item_name", nullable = false, length = 255)
    public String itemName;

    @Column(nullable = false)
    public int quantity;

    @Column(name = "minimum_threshold", nullable = false)
    public int minimumThreshold;

    @Column(name = "last_updated", nullable = false)
    public LocalDateTime lastUpdated;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (id == null) {
            id = UUID.randomUUID();
        }
        createdAt = now;
        lastUpdated = now;
    }

    @PreUpdate
    void preUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
