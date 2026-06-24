-- Walk-in dine-in order, completed and paid
INSERT INTO orders (id, order_number, type, status, restaurant_table_id, customer_id, staff_id, special_requests, total_amount, created_at, updated_at)
VALUES
    ('70000000-0000-0000-0000-000000000001', 'ORD-2026-001', 'DINE_IN', 'COMPLETED',
     '20000000-0000-0000-0000-000000000003',  -- T3
     '10000000-0000-0000-0000-000000000008',  -- Aina Batrisyia
     '10000000-0000-0000-0000-000000000004',  -- staff1
     'No peanuts please', 32.80, '2026-06-20 12:30:00', '2026-06-20 13:15:00')
ON CONFLICT (id) DO NOTHING;

INSERT INTO orders (id, order_number, type, status, restaurant_table_id, customer_id, staff_id, special_requests, total_amount, created_at, updated_at)
VALUES
    ('70000000-0000-0000-0000-000000000002', 'ORD-2026-002', 'DINE_IN', 'COMPLETED',
     '20000000-0000-0000-0000-000000000005',  -- T5
     '10000000-0000-0000-0000-000000000009',  -- Daniel Wong
     '10000000-0000-0000-0000-000000000005',  -- staff2
     NULL, 23.90, '2026-06-21 18:00:00', '2026-06-21 18:50:00')
ON CONFLICT (id) DO NOTHING;

-- Reservation order, converted from reservation #1 (anniversary dinner)
INSERT INTO orders (id, order_number, type, status, restaurant_table_id, reservation_id, customer_id, staff_id, special_requests, total_amount, created_at, updated_at)
VALUES
    ('70000000-0000-0000-0000-000000000003', 'ORD-2026-003', 'RESERVATION', 'SERVED',
     '20000000-0000-0000-0000-000000000006',  -- T6
     '60000000-0000-0000-0000-000000000001',  -- reservation #1
     '10000000-0000-0000-0000-000000000008',  -- Aina Batrisyia
     '10000000-0000-0000-0000-000000000004',  -- staff1
     'Anniversary dinner, requested a quiet corner', 84.00, '2026-06-25 18:45:00', '2026-06-25 19:50:00')
ON CONFLICT (id) DO NOTHING;

INSERT INTO orders (id, order_number, type, status, restaurant_table_id, customer_id, staff_id, special_requests, total_amount, created_at, updated_at)
VALUES
    ('70000000-0000-0000-0000-000000000004', 'ORD-2026-004', 'DINE_IN', 'PREPARING',
     '20000000-0000-0000-0000-000000000002',  -- T2
     '10000000-0000-0000-0000-000000000010',  -- Farah Izzati
     '10000000-0000-0000-0000-000000000006',  -- staff3
     NULL, 15.90, '2026-06-25 12:05:00', '2026-06-25 12:05:00')
ON CONFLICT (id) DO NOTHING;

-- Walk-in customer, no account
INSERT INTO orders (id, order_number, type, status, restaurant_table_id, customer_id, staff_id, special_requests, total_amount, created_at, updated_at)
VALUES
    ('70000000-0000-0000-0000-000000000005', 'ORD-2026-005', 'DINE_IN', 'CONFIRMED',
     '20000000-0000-0000-0000-000000000004',  -- T4
     NULL,
     '10000000-0000-0000-0000-000000000005',  -- staff2
     'Walk-in, no rice please', 30.80, '2026-06-25 13:10:00', '2026-06-25 13:10:00')
ON CONFLICT (id) DO NOTHING;

-- Reservation order linked to cancelled reservation #2
INSERT INTO orders (id, order_number, type, status, restaurant_table_id, reservation_id, customer_id, staff_id, special_requests, total_amount, created_at, updated_at)
VALUES
    ('70000000-0000-0000-0000-000000000006', 'ORD-2026-006', 'RESERVATION', 'CANCELLED',
     '20000000-0000-0000-0000-000000000007',  -- T7
     '60000000-0000-0000-0000-000000000002',  -- reservation #2
     '10000000-0000-0000-0000-000000000008',  -- Aina Batrisyia
     '10000000-0000-0000-0000-000000000007',  -- staff4
     NULL, 18.90, '2026-06-24 19:30:00', '2026-06-24 20:10:00')
ON CONFLICT (id) DO NOTHING;

INSERT INTO orders (id, order_number, type, status, restaurant_table_id, customer_id, staff_id, special_requests, total_amount, created_at, updated_at)
VALUES
    ('70000000-0000-0000-0000-000000000007', 'ORD-2026-007', 'DINE_IN', 'PENDING',
     '20000000-0000-0000-0000-000000000001',  -- T1
     '10000000-0000-0000-0000-000000000009',  -- Daniel Wong
     '10000000-0000-0000-0000-000000000004',  -- staff1
     NULL, 20.00, '2026-06-25 17:40:00', '2026-06-25 17:40:00')
ON CONFLICT (id) DO NOTHING;

INSERT INTO orders (id, order_number, type, status, restaurant_table_id, customer_id, staff_id, special_requests, total_amount, created_at, updated_at)
VALUES
    ('70000000-0000-0000-0000-000000000008', 'ORD-2026-008', 'DINE_IN', 'COMPLETED',
     '20000000-0000-0000-0000-000000000008',  -- T8
     '10000000-0000-0000-0000-000000000010',  -- Farah Izzati
     '10000000-0000-0000-0000-000000000006',  -- staff3
     NULL, 64.80, '2026-06-22 19:00:00', '2026-06-22 20:05:00')
ON CONFLICT (id) DO NOTHING;

INSERT INTO orders (id, order_number, type, status, restaurant_table_id, customer_id, staff_id, special_requests, total_amount, created_at, updated_at)
VALUES
    ('70000000-0000-0000-0000-000000000009', 'ORD-2026-009', 'DINE_IN', 'READY',
     '20000000-0000-0000-0000-000000000009',  -- T9
     '10000000-0000-0000-0000-000000000011',  -- Raj Kumar
     '10000000-0000-0000-0000-000000000005',  -- staff2
     NULL, 24.90, '2026-06-25 18:20:00', '2026-06-25 18:50:00')
ON CONFLICT (id) DO NOTHING;

INSERT INTO orders (id, order_number, type, status, restaurant_table_id, customer_id, staff_id, special_requests, total_amount, created_at, updated_at)
VALUES
    ('70000000-0000-0000-0000-000000000010', 'ORD-2026-010', 'DINE_IN', 'DRAFT',
     '20000000-0000-0000-0000-000000000010',  -- T10
     '10000000-0000-0000-0000-000000000008',  -- Aina Batrisyia
     '10000000-0000-0000-0000-000000000007',  -- staff4
     'Seasonal dessert tasting', 24.00, '2026-06-25 20:00:00', '2026-06-25 20:00:00')
ON CONFLICT (id) DO NOTHING;
