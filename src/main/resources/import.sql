INSERT INTO inventory_items (sku, name, category, unit, qty_on_hand, reorder_level, status)
VALUES
    ('ING-CHICK-001', 'Chicken Breast', 'Protein', 'kg', 40, 15, 'ACTIVE'),
    ('ING-RICE-001', 'Jasmine Rice', 'Dry Goods', 'kg', 55, 20, 'ACTIVE'),
    ('BEV-COLA-001', 'Cola Syrup', 'Beverage', 'liter', 8, 10, 'ACTIVE')
ON CONFLICT (sku) DO NOTHING;
