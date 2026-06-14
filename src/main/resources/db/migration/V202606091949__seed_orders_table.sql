INSERT INTO orders (id, order_number, type, status, restaurant_table_id, customer_id, staff_id, special_requests, total_amount)
VALUES
    ('e5000000-0000-0000-0000-000000000001',
     'ORD-001',
     'DINE_IN',
     'CONFIRMED',
     'b2000000-0000-0000-0000-000000000001',  -- T1
     '9e740ce5-5aef-41d0-a8ff-23b6491ca29e',  -- John
     'd5ea6c50-cfe8-4d3a-a85c-f3956bf8aa81',  -- Jane
     'No peanuts please',
     28.40)
ON CONFLICT (id) DO NOTHING;

-- Reservation order (converted from reservation #1)
INSERT INTO orders (id, order_number, type, status, restaurant_table_id, reservation_id, customer_id, staff_id, total_amount)
VALUES
    ('e5000000-0000-0000-0000-000000000002',
     'ORD-002',
     'RESERVATION',
     'PREPARING',
     'b2000000-0000-0000-0000-000000000002',  -- T2
     'd4000000-0000-0000-0000-000000000001',  -- reservation #1
     '56aaa010-c314-49bd-a3c9-d70895afca78',  -- Jane Doe
     'd5ea6c50-cfe8-4d3a-a85c-f3956bf8aa81',  -- Staff 1
     32.40)
ON CONFLICT (id) DO NOTHING;