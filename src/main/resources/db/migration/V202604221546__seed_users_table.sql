-- All seeded accounts share the password: password
INSERT INTO users
(id,username,password_hash,full_name,"role",phone_number,created_at,updated_at)
VALUES
    ('10000000-0000-0000-0000-000000000001'::uuid,'admin1','$2a$10$79VwRQNh2k7miq77.pOpTe5A.L1PUD/U2//43ronfToI8Pf98cwk2','Ahmad Faiz Hassan','admin','+60123456701','2026-01-05 08:00:00','2026-01-05 08:00:00'),
    ('10000000-0000-0000-0000-000000000002'::uuid,'manager1','$2a$10$79VwRQNh2k7miq77.pOpTe5A.L1PUD/U2//43ronfToI8Pf98cwk2','Siti Nurhaliza Rahman','manager','+60123456702','2026-01-05 08:05:00','2026-01-05 08:05:00'),
    ('10000000-0000-0000-0000-000000000003'::uuid,'manager2','$2a$10$79VwRQNh2k7miq77.pOpTe5A.L1PUD/U2//43ronfToI8Pf98cwk2','Tan Wei Liang','manager','+60123456703','2026-01-06 09:10:00','2026-01-06 09:10:00'),
    ('10000000-0000-0000-0000-000000000004'::uuid,'staff1','$2a$10$79VwRQNh2k7miq77.pOpTe5A.L1PUD/U2//43ronfToI8Pf98cwk2','Muhammad Hafiz Zulkifli','staff','+60123456704','2026-01-10 10:00:00','2026-01-10 10:00:00'),
    ('10000000-0000-0000-0000-000000000005'::uuid,'staff2','$2a$10$79VwRQNh2k7miq77.pOpTe5A.L1PUD/U2//43ronfToI8Pf98cwk2','Nur Aisyah Kamal','staff','+60123456705','2026-01-10 10:05:00','2026-01-10 10:05:00'),
    ('10000000-0000-0000-0000-000000000006'::uuid,'staff3','$2a$10$79VwRQNh2k7miq77.pOpTe5A.L1PUD/U2//43ronfToI8Pf98cwk2','Kavitha Rajan','staff','+60123456706','2026-01-12 11:00:00','2026-01-12 11:00:00'),
    ('10000000-0000-0000-0000-000000000007'::uuid,'staff4','$2a$10$79VwRQNh2k7miq77.pOpTe5A.L1PUD/U2//43ronfToI8Pf98cwk2','Lim Jun Hao','staff','+60123456707','2026-01-12 11:05:00','2026-01-12 11:05:00'),
    ('10000000-0000-0000-0000-000000000008'::uuid,'aina.b','$2a$10$79VwRQNh2k7miq77.pOpTe5A.L1PUD/U2//43ronfToI8Pf98cwk2','Aina Batrisyia','customer','+60123456708','2026-02-01 12:00:00','2026-02-01 12:00:00'),
    ('10000000-0000-0000-0000-000000000009'::uuid,'daniel.wong','$2a$10$79VwRQNh2k7miq77.pOpTe5A.L1PUD/U2//43ronfToI8Pf98cwk2','Daniel Wong','customer','+60123456709','2026-02-03 13:30:00','2026-02-03 13:30:00'),
    ('10000000-0000-0000-0000-000000000010'::uuid,'farah.izzati','$2a$10$79VwRQNh2k7miq77.pOpTe5A.L1PUD/U2//43ronfToI8Pf98cwk2','Farah Izzati','customer','+60123456710','2026-02-10 14:45:00','2026-02-10 14:45:00'),
    ('10000000-0000-0000-0000-000000000011'::uuid,'raj.kumar','$2a$10$79VwRQNh2k7miq77.pOpTe5A.L1PUD/U2//43ronfToI8Pf98cwk2','Raj Kumar','customer','+60123456711','2026-02-15 16:20:00','2026-02-15 16:20:00')
ON CONFLICT (id) DO NOTHING;