CREATE TABLE IF NOT EXISTS inventory_movements (
    id UUID PRIMARY KEY,
    item_id UUID NOT NULL REFERENCES inventory(id) ON DELETE CASCADE,
    movement_type VARCHAR(20) NOT NULL CHECK (movement_type IN ('IN', 'OUT', 'ADJUST')),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    reference VARCHAR(150),
    note VARCHAR(500),
    performed_by VARCHAR(150),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_inventory_movements_item_created
    ON inventory_movements (item_id, created_at DESC);

CREATE INDEX IF NOT EXISTS idx_inventory_movements_created_at
    ON inventory_movements (created_at DESC);
