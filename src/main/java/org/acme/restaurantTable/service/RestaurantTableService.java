package org.acme.restaurantTable.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.acme.restaurantTable.dto.CreateRestaurantTableRequest;
import org.acme.restaurantTable.dto.UpdateRestaurantTableRequest;
import org.acme.restaurantTable.model.RestaurantTable;
import org.acme.restaurantTable.repository.RestaurantTableRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RestaurantTableService {

    @Inject
    RestaurantTableRepository tableRepository;

    public List<RestaurantTable> listTables(boolean activeOnly) {
        if (activeOnly) {
            return tableRepository.findActive();
        }
        return tableRepository.listAll();
    }

    public RestaurantTable getTable(UUID id) {
        RestaurantTable table = tableRepository.findById(id);
        if (table == null) {
            throw new NotFoundException("Table not found");
        }
        return table;
    }

    @Transactional
    public RestaurantTable createTable(CreateRestaurantTableRequest request) {
        tableRepository.findByTableNumber(request.tableNumber()).ifPresent(t -> {
            throw new BadRequestException("Table number already exists");
        });

        RestaurantTable table = new RestaurantTable();
        table.setTableNumber(request.tableNumber().trim());
        table.setCapacity(request.capacity());
        table.setIsActive(request.isActive() != null ? request.isActive() : true);

        tableRepository.persist(table);
        return table;
    }

    @Transactional
    public RestaurantTable updateTable(UUID id, UpdateRestaurantTableRequest request) {
        RestaurantTable table = getTable(id);

        if (request.tableNumber() != null) {
            String trimmed = request.tableNumber().trim();
            tableRepository.findByTableNumber(trimmed).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new BadRequestException("Table number already exists");
                }
            });
            table.setTableNumber(trimmed);
        }

        if (request.capacity() != null) {
            table.setCapacity(request.capacity());
        }

        if (request.isActive() != null) {
            table.setIsActive(request.isActive());
        }

        return table;
    }

    @Transactional
    public void deleteTable(UUID id) {
        RestaurantTable table = getTable(id);
        tableRepository.delete(table);
    }
}