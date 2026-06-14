CREATE TABLE IF NOT EXISTS restaurant_tables (
        id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        table_number VARCHAR(10) NOT NULL UNIQUE,
        capacity     INT NOT NULL CHECK (capacity > 0),
        is_active    BOOLEAN NOT NULL DEFAULT TRUE,
        created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
        updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
);