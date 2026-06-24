-- ORD-2026-001: Nasi Lemak Ayam Goreng x2 + Teh Tarik x2 = 32.80
INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000001', 2, 12.90, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000001' AND menu_item_id = '30000000-0000-0000-0000-000000000001');

INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000017', 2, 3.50, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000001' AND menu_item_id = '30000000-0000-0000-0000-000000000017');

-- ORD-2026-002: Chicken Rendang x1 + Mineral Water x1 + Sago Gula Melaka x1 = 23.90
INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000002', 1, 15.90, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000002' AND menu_item_id = '30000000-0000-0000-0000-000000000002');

INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000020', 1, 2.00, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000002' AND menu_item_id = '30000000-0000-0000-0000-000000000020');

INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000015', 1, 6.00, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000002' AND menu_item_id = '30000000-0000-0000-0000-000000000015');

-- ORD-2026-003: Family Feast Set x1 + Iced Lemon Tea x4 = 84.00
INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000003', '30000000-0000-0000-0000-000000000009', 1, 68.00, 'Anniversary - please add a small candle'
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000003' AND menu_item_id = '30000000-0000-0000-0000-000000000009');

INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000003', '30000000-0000-0000-0000-000000000018', 4, 4.00, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000003' AND menu_item_id = '30000000-0000-0000-0000-000000000018');

-- ORD-2026-004: Mee Goreng Mamak x1 + Curry Puff x1 = 15.90
INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000004', '30000000-0000-0000-0000-000000000004', 1, 9.90, 'Less spicy'
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000004' AND menu_item_id = '30000000-0000-0000-0000-000000000004');

INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000004', '30000000-0000-0000-0000-000000000011', 1, 6.00, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000004' AND menu_item_id = '30000000-0000-0000-0000-000000000011');

-- ORD-2026-005: Char Kway Teow x2 + Sirap Bandung x2 = 30.80
INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000005', '30000000-0000-0000-0000-000000000006', 2, 11.90, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000005' AND menu_item_id = '30000000-0000-0000-0000-000000000006');

INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000005', '30000000-0000-0000-0000-000000000019', 2, 3.50, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000005' AND menu_item_id = '30000000-0000-0000-0000-000000000019');

-- ORD-2026-006: Grilled Fish with Sambal x1 = 18.90 (cancelled order)
INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000006', '30000000-0000-0000-0000-000000000007', 1, 18.90, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000006' AND menu_item_id = '30000000-0000-0000-0000-000000000007');

-- ORD-2026-007: Roti Canai with Curry x2 + Teh Tarik x2 = 20.00
INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000007', '30000000-0000-0000-0000-000000000008', 2, 6.50, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000007' AND menu_item_id = '30000000-0000-0000-0000-000000000008');

INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000007', '30000000-0000-0000-0000-000000000017', 2, 3.50, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000007' AND menu_item_id = '30000000-0000-0000-0000-000000000017');

-- ORD-2026-008: Chef's Special Combo x2 + Fresh Orange Juice x2 = 64.80
INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000008', '30000000-0000-0000-0000-000000000010', 2, 25.90, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000008' AND menu_item_id = '30000000-0000-0000-0000-000000000010');

INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000008', '30000000-0000-0000-0000-000000000021', 2, 6.50, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000008' AND menu_item_id = '30000000-0000-0000-0000-000000000021');

-- ORD-2026-009: Beef Rendang x1 + Ice Kacang x1 = 24.90
INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000009', '30000000-0000-0000-0000-000000000003', 1, 17.90, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000009' AND menu_item_id = '30000000-0000-0000-0000-000000000003');

INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000009', '30000000-0000-0000-0000-000000000016', 1, 7.00, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000009' AND menu_item_id = '30000000-0000-0000-0000-000000000016');

-- ORD-2026-010: Durian Pancake (Seasonal) x2 = 24.00
INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
SELECT '70000000-0000-0000-0000-000000000010', '30000000-0000-0000-0000-000000000022', 2, 12.00, NULL
WHERE NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = '70000000-0000-0000-0000-000000000010' AND menu_item_id = '30000000-0000-0000-0000-000000000022');
