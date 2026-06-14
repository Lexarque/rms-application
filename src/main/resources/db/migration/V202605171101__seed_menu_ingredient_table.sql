INSERT INTO menu_ingredient (id, menu_item_id, inventory_id, quantity_required)
SELECT 'c3d4e5f6-1111-1111-1111-000000000001', 'b2c3d4e5-1111-1111-1111-000000000001', '11111111-1111-1111-1111-111111111111', 1 -- 1 portion Chicken Breast
WHERE NOT EXISTS (SELECT 1 FROM menu_ingredient WHERE id = 'c3d4e5f6-1111-1111-1111-000000000001');

INSERT INTO menu_ingredient (id, menu_item_id, inventory_id, quantity_required)
SELECT 'c3d4e5f6-1111-1111-1111-000000000002', 'b2c3d4e5-1111-1111-1111-000000000001', '22222222-2222-2222-2222-222222222222', 1 -- 1 portion Jasmine Rice
WHERE NOT EXISTS (SELECT 1 FROM menu_ingredient WHERE id = 'c3d4e5f6-1111-1111-1111-000000000002');

INSERT INTO menu_ingredient (id, menu_item_id, inventory_id, quantity_required)
SELECT 'c3d4e5f6-1111-1111-1111-000000000003', 'b2c3d4e5-1111-1111-1111-000000000001', '44444444-4444-4444-4444-444444444444', 2 -- 2 Eggs Grade A
WHERE NOT EXISTS (SELECT 1 FROM menu_ingredient WHERE id = 'c3d4e5f6-1111-1111-1111-000000000003');

-- Recipe 2: Chicken & Fries Basket (Uses Chicken and Fries)
INSERT INTO menu_ingredient (id, menu_item_id, inventory_id, quantity_required)
SELECT 'c3d4e5f6-2222-2222-2222-000000000004', 'b2c3d4e5-2222-2222-2222-000000000002', '11111111-1111-1111-1111-111111111111', 1 -- 1 portion Chicken Breast
WHERE NOT EXISTS (SELECT 1 FROM menu_ingredient WHERE id = 'c3d4e5f6-2222-2222-2222-000000000004');

INSERT INTO menu_ingredient (id, menu_item_id, inventory_id, quantity_required)
SELECT 'c3d4e5f6-2222-2222-2222-000000000005', 'b2c3d4e5-2222-2222-2222-000000000002', '55555555-5555-5555-5555-555555555555', 1 -- 1 portion Frozen Fries
WHERE NOT EXISTS (SELECT 1 FROM menu_ingredient WHERE id = 'c3d4e5f6-2222-2222-2222-000000000005');