INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '11111111-1111-1111-1111-111111111111', 'Chicken Breast', 40, 15, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '11111111-1111-1111-1111-111111111111');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '22222222-2222-2222-2222-222222222222', 'Jasmine Rice', 55, 20, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '22222222-2222-2222-2222-222222222222');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '33333333-3333-3333-3333-333333333333', 'Cola Syrup', 8, 10, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '33333333-3333-3333-3333-333333333333');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '44444444-4444-4444-4444-444444444444', 'Eggs Grade A', 0, 12, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '44444444-4444-4444-4444-444444444444');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '55555555-5555-5555-5555-555555555555', 'Frozen Fries', 12, 12, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '55555555-5555-5555-5555-555555555555');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', 'IN', 50, 'PO-1001', 'Initial stock in', 'system', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '11111111-1111-1111-1111-111111111111', 'OUT', 10, 'SO-2001', 'Kitchen usage', 'system', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', '22222222-2222-2222-2222-222222222222', 'IN', 55, 'PO-1002', 'Initial stock in', 'system', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'ccccccc1-cccc-cccc-cccc-ccccccccccc1', '33333333-3333-3333-3333-333333333333', 'IN', 20, 'PO-1003', 'Initial stock in', 'system', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'ccccccc1-cccc-cccc-cccc-ccccccccccc1');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'ccccccc2-cccc-cccc-cccc-ccccccccccc2', '33333333-3333-3333-3333-333333333333', 'OUT', 12, 'SO-2002', 'Bar consumption', 'system', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'ccccccc2-cccc-cccc-cccc-ccccccccccc2');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'ddddddd1-dddd-dddd-dddd-ddddddddddd1', '44444444-4444-4444-4444-444444444444', 'ADJUST', 5, 'ADJ-3001', 'Physical count adjustment', 'manager', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'ddddddd1-dddd-dddd-dddd-ddddddddddd1');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'eeeeeee1-eeee-eeee-eeee-eeeeeeeeeee1', '55555555-5555-5555-5555-555555555555', 'IN', 12, 'PO-1004', 'Initial stock in', 'system', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'eeeeeee1-eeee-eeee-eeee-eeeeeeeeeee1');
