package org.acme.itemMenu.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.itemMenu.model.MenuItem;

import java.util.UUID;

@ApplicationScoped
public class MenuItemRepository implements PanacheRepositoryBase<MenuItem, UUID> {
}