package org.acme.itemMenu.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.itemMenu.model.MenuIngredient;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MenuIngredientRepository implements PanacheRepositoryBase<MenuIngredient, UUID> {

    public List<MenuIngredient> findByMenuItem(UUID menuItemId) {
        return find(
            "select mi from MenuIngredient mi join fetch mi.inventoryItem where mi.menuItem.id = ?1",
            menuItemId
        ).list();
    }

    public void deleteByMenuItem(UUID menuItemId) {
        delete("menuItem.id", menuItemId);
    }
}