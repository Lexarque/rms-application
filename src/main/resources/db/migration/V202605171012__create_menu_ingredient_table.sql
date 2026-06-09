CREATE TABLE menu_ingredient (
    id UUID PRIMARY KEY,
    menu_item_id UUID NOT NULL,
    inventory_id UUID NOT NULL,
    quantity_required INTEGER NOT NULL,

    CONSTRAINT fk_menu_item
        FOREIGN KEY (menu_item_id)
        REFERENCES menu_item(id),

    CONSTRAINT fk_inventory
        FOREIGN KEY (inventory_id)
        REFERENCES inventory(id)
);