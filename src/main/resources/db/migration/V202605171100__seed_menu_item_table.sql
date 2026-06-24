-- FOOD
INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000001', 'Nasi Lemak Ayam Goreng', 'Fragrant coconut rice with fried chicken, sambal, anchovies, peanuts and egg', 12.90, 'https://commons.wikimedia.org/wiki/Special:FilePath/Nasi_lemak_on_banana_leaf.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000001');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000002', 'Chicken Rendang with Rice', 'Slow-cooked chicken in rich coconut rendang sauce, served with jasmine rice', 15.90, 'https://commons.wikimedia.org/wiki/Special:FilePath/Rendang_Ayam_Cili_Api_Negeri_Sembilan.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000002');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000003', 'Beef Rendang with Rice', 'Tender beef simmered in spiced coconut rendang sauce, served with jasmine rice', 17.90, 'https://commons.wikimedia.org/wiki/Special:FilePath/Rendang_daging_sapi_asli_Padang.JPG', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000003');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000004', 'Mee Goreng Mamak', 'Spicy stir-fried yellow noodles with egg, tofu and vegetables', 9.90, 'https://commons.wikimedia.org/wiki/Special:FilePath/Mi_goreng_tek-tek.JPG', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000004');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000005', 'Nasi Goreng Kampung', 'Village-style fried rice with anchovies, egg and sambal', 10.90, 'https://commons.wikimedia.org/wiki/Special:FilePath/Nasi_goreng_kampung.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000005');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000006', 'Char Kway Teow', 'Wok-fried flat rice noodles with egg, prawns and bean sprouts', 11.90, 'https://commons.wikimedia.org/wiki/Special:FilePath/Char_Kuey_Teow_Basah.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000006');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000007', 'Grilled Fish with Sambal', 'Whole tilapia grilled and topped with spicy sambal', 18.90, 'https://commons.wikimedia.org/wiki/Special:FilePath/Ikan_Bawal_Bakar_dengan_Sambal.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000007');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000008', 'Roti Canai with Curry', 'Flaky flatbread served with chicken curry dipping sauce', 6.50, 'https://commons.wikimedia.org/wiki/Special:FilePath/Roti_canai_with_dal_in_Kuala_Lumpur%2C_July_2014.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000008');

-- SET MEAL
INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000009', 'Family Feast Set (4 pax)', 'Mixed rice, rendang, fried chicken, vegetables and drinks for 4 people', 68.00, 'https://commons.wikimedia.org/wiki/Special:FilePath/Nasi_ramas_rendang.JPG', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000009');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000010', 'Chef''s Special Combo', 'Grilled chicken, fries, coleslaw and a drink of your choice', 25.90, 'https://commons.wikimedia.org/wiki/Special:FilePath/Masala_Grilled_Chicken_with_Baby_Potatoes%2C_Salad.JPG', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000010');

-- SNACK
INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000011', 'Curry Puff (3 pcs)', 'Crispy pastry filled with spiced chicken and potato', 6.00, 'https://commons.wikimedia.org/wiki/Special:FilePath/Karipap_Daging.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000011');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000012', 'Crispy Popiah (4 pcs)', 'Fresh spring rolls with turnip, egg and crispy shallots', 7.50, 'https://commons.wikimedia.org/wiki/Special:FilePath/Popiah.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000012');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000013', 'French Fries', 'Crispy golden fries served with chili sauce', 7.90, 'https://commons.wikimedia.org/wiki/Special:FilePath/French_Fries.JPG', FALSE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000013');

-- DESSERT
INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000014', 'Cendol', 'Iced dessert with green rice jelly, coconut milk and palm sugar syrup', 5.50, 'https://commons.wikimedia.org/wiki/Special:FilePath/Cendol_in_a_Glass.JPG', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000014');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000015', 'Sago Gula Melaka', 'Sago pearls in chilled coconut milk and palm sugar syrup', 6.00, 'https://commons.wikimedia.org/wiki/Special:FilePath/Sagu_gula_melaka.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000015');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000016', 'Ice Kacang', 'Shaved ice dessert with red beans, sweet corn, jelly and syrup', 7.00, 'https://commons.wikimedia.org/wiki/Special:FilePath/Ais_Kacang.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000016');

-- BEVERAGE
INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000017', 'Teh Tarik', 'Frothy pulled milk tea', 3.50, 'https://commons.wikimedia.org/wiki/Special:FilePath/Teh_Tarik.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000017');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000018', 'Iced Lemon Tea', 'Refreshing iced black tea with fresh lemon', 4.00, 'https://commons.wikimedia.org/wiki/Special:FilePath/Iced_lemon_tea.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000018');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000019', 'Sirap Bandung', 'Rose syrup drink with evaporated milk', 3.50, 'https://commons.wikimedia.org/wiki/Special:FilePath/Bandung_Drinks.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000019');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000020', 'Mineral Water', 'Bottled mineral water 500ml', 2.00, 'https://commons.wikimedia.org/wiki/Special:FilePath/A_bottle_of_Aqua_mineral_water.JPG', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000020');

INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000021', 'Fresh Orange Juice', 'Freshly squeezed orange juice', 6.50, 'https://commons.wikimedia.org/wiki/Special:FilePath/A_glass_of_orange_juice_%282014-12-23%29.JPG', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000021');

-- SEASONAL
INSERT INTO menu_item (id, item_name, description, price, image_url, is_available, created_at, last_updated)
SELECT '30000000-0000-0000-0000-000000000022', 'Durian Pancake (Seasonal)', 'Crepe filled with fresh musang king durian cream, available during durian season', 12.00, 'https://commons.wikimedia.org/wiki/Special:FilePath/Durian_Pancake.jpg', TRUE, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = '30000000-0000-0000-0000-000000000022');
