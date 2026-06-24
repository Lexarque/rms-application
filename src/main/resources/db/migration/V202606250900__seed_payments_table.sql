-- Paid in full for completed/served orders
INSERT INTO payments (id, order_id, amount, method, status, cash_tendered, change_amount, created_at, updated_at)
SELECT '80000000-0000-0000-0000-000000000001', '70000000-0000-0000-0000-000000000001', 32.80, 'CASH', 'PAID', 50.00, 17.20, '2026-06-20 13:15:00', '2026-06-20 13:15:00'
WHERE NOT EXISTS (SELECT 1 FROM payments WHERE id = '80000000-0000-0000-0000-000000000001');

INSERT INTO payments (id, order_id, amount, method, status, cash_tendered, change_amount, created_at, updated_at)
SELECT '80000000-0000-0000-0000-000000000002', '70000000-0000-0000-0000-000000000002', 23.90, 'CARD', 'PAID', NULL, NULL, '2026-06-21 18:50:00', '2026-06-21 18:50:00'
WHERE NOT EXISTS (SELECT 1 FROM payments WHERE id = '80000000-0000-0000-0000-000000000002');

INSERT INTO payments (id, order_id, amount, method, status, cash_tendered, change_amount, created_at, updated_at)
SELECT '80000000-0000-0000-0000-000000000003', '70000000-0000-0000-0000-000000000003', 84.00, 'CARD', 'PAID', NULL, NULL, '2026-06-25 19:50:00', '2026-06-25 19:50:00'
WHERE NOT EXISTS (SELECT 1 FROM payments WHERE id = '80000000-0000-0000-0000-000000000003');

INSERT INTO payments (id, order_id, amount, method, status, cash_tendered, change_amount, created_at, updated_at)
SELECT '80000000-0000-0000-0000-000000000004', '70000000-0000-0000-0000-000000000008', 64.80, 'CASH', 'PAID', 70.00, 5.20, '2026-06-22 20:05:00', '2026-06-22 20:05:00'
WHERE NOT EXISTS (SELECT 1 FROM payments WHERE id = '80000000-0000-0000-0000-000000000004');

-- Order still in progress, payment not collected yet
INSERT INTO payments (id, order_id, amount, method, status, cash_tendered, change_amount, created_at, updated_at)
SELECT '80000000-0000-0000-0000-000000000005', '70000000-0000-0000-0000-000000000004', 15.90, 'CASH', 'PENDING', NULL, NULL, '2026-06-25 12:05:00', '2026-06-25 12:05:00'
WHERE NOT EXISTS (SELECT 1 FROM payments WHERE id = '80000000-0000-0000-0000-000000000005');

-- Card declined on the cancelled reservation order
INSERT INTO payments (id, order_id, amount, method, status, cash_tendered, change_amount, created_at, updated_at)
SELECT '80000000-0000-0000-0000-000000000006', '70000000-0000-0000-0000-000000000006', 18.90, 'CARD', 'FAILED', NULL, NULL, '2026-06-24 20:05:00', '2026-06-24 20:05:00'
WHERE NOT EXISTS (SELECT 1 FROM payments WHERE id = '80000000-0000-0000-0000-000000000006');
