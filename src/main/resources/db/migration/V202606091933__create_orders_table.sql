CREATE TABLE IF NOT EXISTS orders (
     id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     order_number    VARCHAR(20) NOT NULL UNIQUE,
     type            VARCHAR(15) NOT NULL CHECK (type IN ('DINE_IN', 'RESERVATION')),
     status          VARCHAR(20) NOT NULL DEFAULT 'DRAFT' CHECK (status IN ('DRAFT','PENDING','CONFIRMED','PREPARING','READY','SERVED','COMPLETED','CANCELLED')),
     restaurant_table_id        UUID NOT NULL REFERENCES restaurant_tables(id),
     reservation_id  UUID REFERENCES reservations(id),
     customer_id     UUID REFERENCES users(id),
     staff_id        UUID REFERENCES users(id),
     special_requests TEXT,
     total_amount    DECIMAL(10,2),
     created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
     updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
);