CREATE TABLE IF NOT EXISTS inventory_items (
    id UUID PRIMARY KEY,
    sku VARCHAR(50) NOT NULL,
    name VARCHAR(150) NOT NULL,
    category VARCHAR(80) NOT NULL,
    unit VARCHAR(20) NOT NULL,
    qty_on_hand INTEGER NOT NULL DEFAULT 0 CHECK (qty_on_hand >= 0),
    reorder_level INTEGER NOT NULL DEFAULT 0 CHECK (reorder_level >= 0),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS inventory_transactions (
    id UUID PRIMARY KEY,
    item_id UUID NOT NULL REFERENCES inventory_items(id) ON DELETE RESTRICT,
    txn_type VARCHAR(10) NOT NULL CHECK (txn_type IN ('IN', 'OUT', 'ADJUST')),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    note TEXT,
    ref_no VARCHAR(100),
    created_by VARCHAR(100),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_inventory_items_sku
    ON inventory_items (sku);

CREATE INDEX IF NOT EXISTS idx_inventory_items_name
    ON inventory_items (name);

CREATE INDEX IF NOT EXISTS idx_inventory_items_category
    ON inventory_items (category);

CREATE INDEX IF NOT EXISTS idx_inventory_items_status
    ON inventory_items (status);
