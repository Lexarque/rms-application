package org.acme.restaurantTable.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.restaurantTable.model.RestaurantTable;
import org.acme.shared.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RestaurantTableRepository extends BaseRepository<RestaurantTable> {

    public Optional<RestaurantTable> findByTableNumber(String tableNumber) {
        return find("tableNumber", tableNumber).firstResultOptional();
    }

    public List<RestaurantTable> findActive() {
        return find("isActive", true).list();
    }

    public List<RestaurantTable> findActiveWithCapacityAtLeast(int partySize) {
        return find("isActive = true and capacity >= ?1", partySize).list();
    }
}