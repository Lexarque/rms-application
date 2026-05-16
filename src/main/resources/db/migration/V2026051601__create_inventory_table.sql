CREATE TABLE IF NOT EXISTS inventory (
    id UUID PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    minimum_threshold INTEGER NOT NULL DEFAULT 0 CHECK (minimum_threshold >= 0),
    last_updated TIMESTAMP NOT NULL DEFAULT NOW(),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_inventory_item_name ON inventory (item_name);
CREATE INDEX IF NOT EXISTS idx_inventory_qty_threshold ON inventory (quantity, minimum_threshold);
CREATE INDEX IF NOT EXISTS idx_inventory_last_updated ON inventory (last_updated DESC);
