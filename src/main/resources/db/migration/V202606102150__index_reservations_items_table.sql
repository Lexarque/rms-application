CREATE INDEX IF NOT EXISTS idx_reservations_customer_id       ON reservations(customer_id);
CREATE INDEX IF NOT EXISTS idx_reservations_restaurant_table_id          ON reservations(restaurant_table_id);
CREATE INDEX IF NOT EXISTS idx_reservations_reservation_time  ON reservations(reservation_time);
CREATE INDEX IF NOT EXISTS idx_reservations_status            ON reservations(status);