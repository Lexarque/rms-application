package org.acme.itemMenu.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "menu_item")
public class MenuItem extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "item_name", nullable = false)
    public String itemName;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column(nullable = false, precision = 10, scale = 2)
    public BigDecimal price;

    @Column(name = "image_url")
    public String imageUrl;

    @Column(name = "is_available", nullable = false)
    public Boolean isAvailable = true;

    @Column(name = "created_at", nullable = false)
    public LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_updated", nullable = false)
    public LocalDateTime lastUpdated = LocalDateTime.now();

    // ✅ ADD THIS (fix getId issue)
    public UUID getId() {
        return id;
    }
}