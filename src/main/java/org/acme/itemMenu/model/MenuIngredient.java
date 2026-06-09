package org.acme.itemMenu.model;

import org.acme.inventories.model.InventoryItem;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "menu_ingredient")
public class MenuIngredient extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id")
    public MenuItem menuItem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inventory_item_id")
    public InventoryItem inventoryItem;

    @Column(name = "quantity_required", nullable = false)
    public Integer quantityRequired;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "last_updated", nullable = false)
    public LocalDateTime lastUpdated;

    /* =========================
       LIFECYCLE HOOKS
    ========================= */

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.lastUpdated = now;

        if (this.quantityRequired == null) {
            this.quantityRequired = 0;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }
}