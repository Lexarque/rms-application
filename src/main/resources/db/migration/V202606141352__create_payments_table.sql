CREATE TABLE IF NOT EXISTS payments (
     id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     order_id        UUID NOT NULL REFERENCES orders(id),
     amount          DECIMAL(10,2) NOT NULL,
     method          VARCHAR(10) NOT NULL CHECK (method IN ('CASH', 'CARD')),
     status          VARCHAR(10) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'PAID', 'FAILED')),
     cash_tendered   DECIMAL(10,2),
     change_amount   DECIMAL(10,2),
     created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
     updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
);