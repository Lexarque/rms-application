CREATE TABLE IF NOT EXISTS reservations (
      id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
      customer_id       UUID NOT NULL REFERENCES users(id),
      restaurant_table_id          UUID NOT NULL REFERENCES restaurant_tables(id),
      reservation_time  TIMESTAMP NOT NULL,
      party_size        INT NOT NULL CHECK (party_size > 0),
      status            VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING','CONFIRMED','CANCELLED','SEATED')),
      special_requests  TEXT,
      created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
      updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
);