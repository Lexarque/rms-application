INSERT INTO reservations (id, customer_id, restaurant_table_id, reservation_time, party_size, status, special_requests)
VALUES
    ('d4000000-0000-0000-0000-000000000001',
     'b5321608-7666-4f10-9991-5bbb55d094d5',  -- Alice
     'b2000000-0000-0000-0000-000000000002',  -- T2
     '2026-06-11 19:00:00+08',
     3,
     'CONFIRMED',
     'Near window, birthday celebration')
ON CONFLICT (id) DO NOTHING;