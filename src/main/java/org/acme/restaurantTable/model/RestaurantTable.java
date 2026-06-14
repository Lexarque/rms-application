package org.acme.restaurantTable.model;

import jakarta.persistence.*;
import org.acme.shared.model.BaseEntity;

@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable extends BaseEntity {

    @Column(name = "table_number", nullable = false, unique = true, length = 10)
    public String tableNumber;

    @Column(name = "capacity", nullable = false)
    public Integer capacity;

    @Column(name = "is_active", nullable = false)
    public Boolean isActive;

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}