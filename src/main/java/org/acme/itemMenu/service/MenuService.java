package org.acme.itemMenu.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.acme.inventories.model.InventoryItem;
import org.acme.inventories.repository.InventoryItemRepository;
import org.acme.itemMenu.dto.AddMenuIngredientRequest;
import org.acme.itemMenu.dto.CreateMenuItemRequest;
import org.acme.itemMenu.dto.UpdateMenuItemRequest;
import org.acme.itemMenu.model.MenuIngredient;
import org.acme.itemMenu.model.MenuItem;
import org.acme.itemMenu.repository.MenuIngredientRepository;
import org.acme.itemMenu.repository.MenuItemRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@ApplicationScoped
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final MenuIngredientRepository menuIngredientRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public MenuService(MenuItemRepository menuItemRepository,
                       MenuIngredientRepository menuIngredientRepository,
                       InventoryItemRepository inventoryItemRepository) {
        this.menuItemRepository = menuItemRepository;
        this.menuIngredientRepository = menuIngredientRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public List<MenuItem> listItems(String q,
                                    Boolean isAvailable,
                                    int page,
                                    int size,
                                    String sort) {

        Sort panacheSort = Sort.by("lastUpdated").descending();

        List<MenuItem> items = menuItemRepository.findAll(panacheSort).list();

        if (q != null && !q.isBlank()) {
            String normalized = q.toLowerCase(Locale.ROOT);
            items = items.stream()
                    .filter(i -> i.itemName.toLowerCase().contains(normalized))
                    .toList();
        }

        if (isAvailable != null) {
            items = items.stream()
                    .filter(i -> Boolean.TRUE.equals(i.isAvailable) == isAvailable)
                    .toList();
        }

        int from = page * size;
        int to = Math.min(from + size, items.size());

        if (from >= items.size()) return List.of();

        return items.subList(from, to);
    }

    public MenuItem getItem(UUID id) {
        MenuItem item = menuItemRepository.findById(id);
        if (item == null) throw new NotFoundException("Menu item not found");
        return item;
    }

    @Transactional
    public MenuItem createItem(CreateMenuItemRequest request) {

        MenuItem item = new MenuItem();
        item.itemName = request.itemName().trim();
        item.description = request.description();
        item.price = request.price();
        item.imageUrl = request.imageUrl();
        item.isAvailable = request.isAvailable() != null ? request.isAvailable() : true;
        item.createdAt = LocalDateTime.now();
        item.lastUpdated = LocalDateTime.now();

        menuItemRepository.persist(item);
        return item;
    }

    @Transactional
    public MenuItem updateItem(UUID id, UpdateMenuItemRequest request) {

        MenuItem item = getItem(id);

        item.itemName = request.itemName().trim();
        item.description = request.description();
        item.price = request.price();
        item.imageUrl = request.imageUrl();
        item.isAvailable = request.isAvailable();
        item.lastUpdated = LocalDateTime.now();

        return item;
    }

    @Transactional
    public void deleteItem(UUID id) {
        MenuItem item = getItem(id);

        menuIngredientRepository.delete("menuItem.id", item.id);
        menuItemRepository.delete(item);
    }

    @Transactional
    public MenuIngredient addIngredient(UUID menuItemId,
                                        AddMenuIngredientRequest request) {

        MenuItem menuItem = getItem(menuItemId);

        InventoryItem inventoryItem =
                inventoryItemRepository.findById(request.inventoryItemId());

        if (inventoryItem == null)
            throw new NotFoundException("Inventory item not found");

        MenuIngredient ing = new MenuIngredient();
        ing.menuItem = menuItem;
        ing.inventoryItem = inventoryItem;
        ing.quantityRequired = request.quantityRequired();

        menuIngredientRepository.persist(ing);
        return ing;
    }

    public List<MenuIngredient> getIngredients(UUID menuItemId) {
        getItem(menuItemId);

        return menuIngredientRepository.find("menuItem.id", menuItemId).list();
    }

    @Transactional
    public void removeIngredient(UUID menuId, UUID ingredientId) {

        MenuIngredient ing = menuIngredientRepository.findById(ingredientId);

        if (ing == null || !ing.menuItem.id.equals(menuId)) {
            throw new NotFoundException("Ingredient not found");
        }

        menuIngredientRepository.delete(ing);
    }
}