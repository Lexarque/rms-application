-- Seed order items (corrected column names)

INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
VALUES
    ('e5000000-0000-0000-0000-000000000001', 'b2c3d4e5-1111-1111-1111-000000000001', 1, 12.90, NULL),
    ('e5000000-0000-0000-0000-000000000001', 'b2c3d4e5-1111-1111-1111-000000000001', 1, 15.50, NULL);

INSERT INTO order_items (order_id, menu_item_id, quantity, price_at_order, custom_instructions)
VALUES
    ('e5000000-0000-0000-0000-000000000002', 'b2c3d4e5-2222-2222-2222-000000000002', 1, 18.00, NULL),
    ('e5000000-0000-0000-0000-000000000002', 'b2c3d4e5-2222-2222-2222-000000000002', 2, 4.50,  'Extra sugar'),
    ('e5000000-0000-0000-0000-000000000002', 'b2c3d4e5-2222-2222-2222-000000000002', 1, 9.90,  NULL);