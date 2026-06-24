INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000001', 'Chicken Thigh Fillet', 60, 20, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000001');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000002', 'Beef Cubes', 35, 15, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000002');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000003', 'Whole Tilapia Fish', 18, 10, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000003');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000004', 'Jasmine Rice', 90, 25, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000004');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000005', 'Yellow Noodles', 45, 15, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000005');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000006', 'Flat Rice Noodles', 40, 15, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000006');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000007', 'Coconut Milk', 30, 12, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000007');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000008', 'Sambal Paste', 25, 10, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000008');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000009', 'Roti Canai Dough', 70, 20, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000009');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000010', 'Curry Puff Pastry', 5, 15, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000010');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000011', 'Cendol Jelly', 20, 10, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000011');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000012', 'Gula Melaka Syrup', 22, 10, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000012');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000013', 'Teh Tarik Tea Leaves', 12, 10, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000013');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000014', 'Condensed Milk', 28, 10, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000014');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000015', 'Eggs Grade A', 50, 15, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000015');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000016', 'Frozen Fries', 0, 12, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000016');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000017', 'Cooking Oil', 40, 15, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000017');

INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
SELECT '40000000-0000-0000-0000-000000000018', 'Mineral Water Bottles', 120, 30, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory WHERE id = '40000000-0000-0000-0000-000000000018');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '40000000-0000-0000-0000-000000000001', 'IN', 60, 'PO-1001', 'Weekly chicken delivery', 'manager1', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '40000000-0000-0000-0000-000000000001', 'OUT', 15, 'SO-2001', 'Kitchen usage - lunch service', 'staff1', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', '40000000-0000-0000-0000-000000000004', 'IN', 90, 'PO-1002', 'Rice stock replenishment', 'manager1', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'ccccccc1-cccc-cccc-cccc-ccccccccccc1', '40000000-0000-0000-0000-000000000007', 'IN', 30, 'PO-1003', 'Coconut milk restock', 'manager2', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'ccccccc1-cccc-cccc-cccc-ccccccccccc1');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'ccccccc2-cccc-cccc-cccc-ccccccccccc2', '40000000-0000-0000-0000-000000000010', 'OUT', 35, 'SO-2002', 'Curry puff prep ran low this week', 'staff3', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'ccccccc2-cccc-cccc-cccc-ccccccccccc2');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'ddddddd1-dddd-dddd-dddd-ddddddddddd1', '40000000-0000-0000-0000-000000000015', 'ADJUST', 5, 'ADJ-3001', 'Physical count adjustment', 'manager1', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'ddddddd1-dddd-dddd-dddd-ddddddddddd1');

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at, updated_at)
SELECT 'eeeeeee1-eeee-eeee-eeee-eeeeeeeeeee1', '40000000-0000-0000-0000-000000000016', 'OUT', 12, 'SO-2003', 'Fries fully depleted during dinner rush', 'staff2', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM inventory_movements WHERE id = 'eeeeeee1-eeee-eeee-eeee-eeeeeeeeeee1');
