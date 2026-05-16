INSERT INTO inventory (id, item_name, quantity, minimum_threshold, last_updated, created_at)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Chicken Breast', 40, 15, NOW(), NOW()),
    ('22222222-2222-2222-2222-222222222222', 'Jasmine Rice', 55, 20, NOW(), NOW()),
    ('33333333-3333-3333-3333-333333333333', 'Cola Syrup', 8, 10, NOW(), NOW()),
    ('44444444-4444-4444-4444-444444444444', 'Eggs Grade A', 0, 12, NOW(), NOW()),
    ('55555555-5555-5555-5555-555555555555', 'Frozen Fries', 12, 12, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO inventory_movements (id, item_id, movement_type, quantity, reference, note, performed_by, created_at)
VALUES
    ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', 'IN', 50, 'PO-1001', 'Initial stock in', 'system', NOW() - INTERVAL '12 days'),
    ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '11111111-1111-1111-1111-111111111111', 'OUT', 10, 'SO-2001', 'Kitchen usage', 'system', NOW() - INTERVAL '2 days'),
    ('bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', '22222222-2222-2222-2222-222222222222', 'IN', 55, 'PO-1002', 'Initial stock in', 'system', NOW() - INTERVAL '10 days'),
    ('ccccccc1-cccc-cccc-cccc-ccccccccccc1', '33333333-3333-3333-3333-333333333333', 'IN', 20, 'PO-1003', 'Initial stock in', 'system', NOW() - INTERVAL '15 days'),
    ('ccccccc2-cccc-cccc-cccc-ccccccccccc2', '33333333-3333-3333-3333-333333333333', 'OUT', 12, 'SO-2002', 'Bar consumption', 'system', NOW() - INTERVAL '1 day'),
    ('ddddddd1-dddd-dddd-dddd-ddddddddddd1', '44444444-4444-4444-4444-444444444444', 'ADJUST', 5, 'ADJ-3001', 'Physical count adjustment', 'manager', NOW() - INTERVAL '3 days'),
    ('eeeeeee1-eeee-eeee-eeee-eeeeeeeeeee1', '55555555-5555-5555-5555-555555555555', 'IN', 12, 'PO-1004', 'Initial stock in', 'system', NOW() - INTERVAL '7 days')
ON CONFLICT (id) DO NOTHING;
