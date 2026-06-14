CREATE INDEX IF NOT EXISTS idx_orders_restaurant_table_id        ON orders(restaurant_table_id);
CREATE INDEX IF NOT EXISTS idx_orders_reservation_id  ON orders(reservation_id);
CREATE INDEX IF NOT EXISTS idx_orders_customer_id     ON orders(customer_id);
CREATE INDEX IF NOT EXISTS idx_orders_staff_id        ON orders(staff_id);
CREATE INDEX IF NOT EXISTS idx_orders_created_at      ON orders(created_at);
CREATE INDEX IF NOT EXISTS idx_orders_type_status     ON orders(type, status);