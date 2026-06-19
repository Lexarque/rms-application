package org.acme.itemMenu.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.acme.inventories.model.InventoryItem;
import org.acme.inventories.repository.InventoryItemRepository;
import org.acme.itemMenu.dto.AddMenuIngredientRequest;
import org.acme.itemMenu.dto.MenuItemResponse;
import org.acme.itemMenu.model.MenuCategory;
import org.acme.itemMenu.model.MenuIngredient;
import org.acme.itemMenu.model.MenuItem;
import org.acme.itemMenu.repository.MenuIngredientRepository;
import org.acme.itemMenu.repository.MenuItemRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MenuService {

    @Inject
    MenuItemRepository menuItemRepository;

    @Inject
    MenuIngredientRepository menuIngredientRepository;

    @Inject
    InventoryItemRepository inventoryItemRepository;

    public List<MenuItem> listItems(String q, Boolean isAvailable, int page, int size) {
        String query = "1=1";
        var params = new io.quarkus.panache.common.Parameters();

        if (q != null && !q.isBlank()) {
            query += " and lower(itemName) like :q";
            params.and("q", "%" + q.toLowerCase() + "%");
        }
        if (isAvailable != null) {
            query += " and isAvailable = :isAvailable";
            params.and("isAvailable", isAvailable);
        }

        List<MenuItem> items = menuItemRepository.find(query, params)
                .page(page, size)
                .list();

        for (MenuItem item : items) {
            item.setAvailable(isMenuItemAvailable(item));
        }

        return items;
    }

    public MenuItem getItem(UUID id) {
        MenuItem item = menuItemRepository.findById(id);
        if (item == null) throw new NotFoundException("Menu item not found");
        return item;
    }

    @Transactional
    public MenuItem createItem(String itemName, String description,
                               BigDecimal price, MenuCategory category,
                               String imageUrl, Boolean isAvailable) {
        if (itemName == null || itemName.isBlank()) {
            throw new BadRequestException("Item name is required");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Price must be non-negative");
        }

        MenuItem item = new MenuItem();
        item.setItemName(itemName.trim());
        item.setDescription(description);
        item.setPrice(price);
        item.setCategory(category != null ? category : MenuCategory.FOOD);
        item.setImageUrl(imageUrl);
        item.setAvailable(isAvailable != null ? isAvailable : true);
        item.setCreatedAt(LocalDateTime.now());
        item.setLastUpdated(LocalDateTime.now());

        menuItemRepository.persist(item);
        return item;
    }

    @Transactional
    public MenuItem updateItem(UUID id, String itemName, String description,
                            BigDecimal price, MenuCategory category,
                            String imageUrl, Boolean isAvailable) {

        MenuItem item = getItem(id);

        if (itemName != null && !itemName.isBlank()) item.setItemName(itemName.trim());
        if (description != null) item.setDescription(description);
        if (price != null) item.setPrice(price);
        if (category != null) item.setCategory(category);
        if (imageUrl != null) item.setImageUrl(imageUrl);
        if (isAvailable != null) item.setAvailable(isAvailable);

        item.setLastUpdated(LocalDateTime.now());

        menuItemRepository.persist(item);

        // 🔥 FORCE RELOAD WITH INGREDIENTS (important)
        return menuItemRepository.find(
            "select m from MenuItem m left join fetch m.ingredients where m.id = ?1",
            id
        ).firstResult();
    }

    @Transactional
    public void deleteItem(UUID id) {
        MenuItem item = getItem(id);
        menuIngredientRepository.deleteByMenuItem(id);
        menuItemRepository.delete(item);
    }

    // ---------- Ingredients ----------

    public List<MenuIngredient> listIngredients(UUID menuItemId) {
        getItem(menuItemId);
        return menuIngredientRepository.find(
            "select mi from MenuIngredient mi join fetch mi.inventoryItem where mi.menuItem.id = ?1",
            menuItemId
        ).list();

    }

    @Transactional
    public MenuIngredient addIngredient(UUID menuItemId, AddMenuIngredientRequest request) {
        MenuItem menuItem = getItem(menuItemId);

        InventoryItem inventoryItem = inventoryItemRepository.findById(request.inventoryId());
        if (inventoryItem == null) throw new NotFoundException("Inventory item not found");

        boolean alreadyLinked = menuIngredientRepository
        .find("menuItem.id = ?1 and inventoryItem = ?2",
                menuItemId, inventoryItem)
        .count() > 0;
        if (alreadyLinked) {
            throw new BadRequestException("This ingredient is already linked to the menu item");
        }

        if (request.quantityRequired() == null || request.quantityRequired() <= 0) {
            throw new BadRequestException("quantityRequired must be greater than 0");
        }

        MenuIngredient ingredient = new MenuIngredient();
        ingredient.setMenuItem(menuItem);
        ingredient.setInventoryItem(inventoryItem);
        ingredient.setQuantityRequired(request.quantityRequired());

        menuIngredientRepository.persist(ingredient);
        refreshMenuAvailability(menuItem);
        return ingredient;
    }

    @Transactional
    public void deleteIngredient(UUID menuItemId, UUID inventoryId) {

        MenuIngredient ingredient = menuIngredientRepository
                .find("menuItem.id = ?1 and inventoryItem.id = ?2",
                        menuItemId, inventoryId)
                .firstResult();

        if (ingredient == null) {
            throw new NotFoundException("Ingredient not found for this menu item");
        }

        menuIngredientRepository.delete(ingredient);
        refreshMenuAvailability(ingredient.getMenuItem());
    }

    private boolean isMenuItemAvailable(MenuItem item) {

        if (item.ingredients == null || item.ingredients.isEmpty()) {
            return item.getAvailable();
        }

        return item.ingredients.stream()
                .allMatch(i ->
                        i.getInventoryItem().getQuantity() >= i.getQuantityRequired()
                );
    }

    private void refreshMenuAvailability(MenuItem item) {
        boolean available = isMenuItemAvailable(item);
        item.setAvailable(available);
        item.setLastUpdated(LocalDateTime.now());
    }
}