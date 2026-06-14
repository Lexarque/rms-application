INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT 'b2c3d4e5-1111-1111-1111-000000000001', 'Chicken Fried Rice', 'Savory jasmine rice stir-fried with chicken breast and egg', 14.99, 'https://example.com/fried-rice.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = 'b2c3d4e5-1111-1111-1111-000000000001');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT 'b2c3d4e5-2222-2222-2222-000000000002', 'Chicken & Fries Basket', 'Crispy chicken breast served with a side of golden fries', 12.99, 'https://example.com/chicken-fries.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = 'b2c3d4e5-2222-2222-2222-000000000002');