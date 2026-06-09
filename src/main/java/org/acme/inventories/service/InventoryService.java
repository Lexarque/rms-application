package org.acme.inventories.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.acme.inventories.dto.AdjustQuantityRequest;
import org.acme.inventories.dto.CreateInventoryItemRequest;
import org.acme.inventories.dto.InventoryItemResponse;
import org.acme.inventories.dto.UpdateInventoryItemRequest;
import org.acme.inventories.model.InventoryItem;
import org.acme.inventories.model.InventoryMovement;
import org.acme.inventories.model.MovementType;
import org.acme.inventories.model.StockStatus;
import org.acme.inventories.repository.InventoryItemRepository;
import org.acme.inventories.repository.InventoryMovementRepository;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@ApplicationScoped
public class InventoryService {

    @Inject
    InventoryItemRepository itemRepository;

    @Inject
    InventoryMovementRepository movementRepository;

    public List<InventoryItem> listItems(String q, StockStatus status, int page, int size, String sort) {
        String normalizedQ = normalize(q);
        Sort panacheSort = parseSort(sort);

        List<InventoryItem> items;
        if (normalizedQ == null) {
            items = itemRepository.findAll(panacheSort).list();
        } else {
            items = itemRepository.find(
                    "lower(itemName) like ?1",
                    panacheSort,
                    "%" + normalizedQ.toLowerCase(Locale.ROOT) + "%"
            ).list();
        }

        if (status != null) {
            items = items.stream()
                    .filter(item -> InventoryItemResponse.computeStatus(item.quantity, item.minimumThreshold) == status)
                    .toList();
        }

        int safePage = Math.max(page, 0);
        int safeSize = size <= 0 ? 20 : Math.min(size, 200);
        int from = safePage * safeSize;
        if (from >= items.size()) {
            return List.of();
        }
        int to = Math.min(from + safeSize, items.size());
        return items.subList(from, to);
    }

    public InventoryItem getItem(UUID id) {
        InventoryItem item = itemRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Inventory item not found");
        }
        return item;
    }

    @Transactional
    public InventoryItem createItem(CreateInventoryItemRequest request) {
        InventoryItem item = new InventoryItem();
        item.itemName = request.itemName().trim();
        item.quantity = nonNegativeOrZero(request.quantity(), "quantity");
        item.minimumThreshold = nonNegativeOrZero(request.minimumThreshold(), "minimumThreshold");

        itemRepository.persist(item);
        return item;
    }

    @Transactional
    public InventoryItem updateItem(UUID id, UpdateInventoryItemRequest request) {
        InventoryItem item = getItem(id);

        item.itemName = request.itemName().trim();
        item.quantity = nonNegativeOrZero(request.quantity(), "quantity");
        item.minimumThreshold = nonNegativeOrZero(request.minimumThreshold(), "minimumThreshold");
        item.updatedAt = LocalDateTime.now();

        return item;
    }

    @Transactional
    public InventoryItem adjustQuantity(UUID id, AdjustQuantityRequest request) {
        InventoryItem item = getItem(id);
        int current = item.quantity;
        int newQuantity;

        if (request.movementType() == MovementType.IN) {
            newQuantity = current + request.quantity();
        } else if (request.movementType() == MovementType.OUT) {
            newQuantity = current - request.quantity();
            if (newQuantity < 0) {
                throw new BadRequestException("OUT movement cannot make quantity negative");
            }
        } else {
            newQuantity = request.quantity();
        }

        item.quantity = newQuantity;
        item.updatedAt = LocalDateTime.now();

        InventoryMovement movement = new InventoryMovement();
        movement.item = item;
        movement.movementType = request.movementType();
        movement.quantity = request.quantity();
        movement.note = normalize(request.note());
        movement.reference = normalize(request.reference());
        movement.performedBy = normalize(request.performedBy());
        movementRepository.persist(movement);

        return item;
    }

    @Transactional
    public void deleteItem(UUID id) {
        InventoryItem item = getItem(id);
        itemRepository.delete(item);
    }

    public List<InventoryMovement> listMovements(UUID itemId, String month) {
        YearMonth ym = parseMonth(month);

        String query = "1=1";
        java.util.ArrayList<Object> params = new java.util.ArrayList<>();

        if (itemId != null) {
            getItem(itemId);
            query += " and item.id = ?" + (params.size() + 1);
            params.add(itemId);
        }

        if (ym != null) {
            LocalDateTime from = ym.atDay(1).atStartOfDay();
            LocalDateTime to = ym.plusMonths(1).atDay(1).atStartOfDay();
            query += " and createdAt >= ?" + (params.size() + 1);
            params.add(from);
            query += " and createdAt < ?" + (params.size() + 1);
            params.add(to);
        }

        return movementRepository.find(query, Sort.by("createdAt", Sort.Direction.Descending), params.toArray()).list();
    }

    private int nonNegativeOrZero(Integer value, String field) {
        if (value == null) {
            return 0;
        }
        if (value < 0) {
            throw new BadRequestException(field + " cannot be negative");
        }
        return value;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private YearMonth parseMonth(String month) {
        if (month == null || month.isBlank()) {
            return null;
        }
        try {
            return YearMonth.parse(month.trim());
        } catch (Exception ex) {
            throw new BadRequestException("month must be in YYYY-MM format");
        }
    }

    private Sort parseSort(String sort) {
        String field = "updatedAt";
        Sort.Direction direction = Sort.Direction.Descending;

        if (sort != null && !sort.isBlank()) {
            String[] parts = sort.split(",");
            if (parts.length > 0 && !parts[0].isBlank()) {
                field = switch (parts[0].trim()) {
                    case "item_name", "itemName" -> "itemName";
                    case "quantity" -> "quantity";
                    case "minimum_threshold", "minimumThreshold" -> "minimumThreshold";
                    case "created_at", "createdAt" -> "createdAt";
                    case "last_updated", "updatedAt" -> "updatedAt";
                    default -> throw new BadRequestException("Unsupported sort field: " + parts[0].trim());
                };
            }
            if (parts.length > 1) {
                direction = "asc".equalsIgnoreCase(parts[1].trim())
                        ? Sort.Direction.Ascending
                        : Sort.Direction.Descending;
            }
        }

        return Sort.by(field, direction);
    }
}
