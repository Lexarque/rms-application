INSERT INTO users
    (id,username,password_hash,full_name,"role",phone_number,created_at,updated_at)
VALUES
    ('9e740ce5-5aef-41d0-a8ff-23b6491ca29e'::uuid,'user1','$2a$10$ofr490fAbZEEJDMfLg1wn.fbQUbn9dDdD5ehgpTGa6CB9UQVIIeIW','User 1','customer','+60172652627','2026-05-13 14:40:31.467','2026-05-13 14:40:31.467'),
    ('d5ea6c50-cfe8-4d3a-a85c-f3956bf8aa81'::uuid,'staff2','$2a$10$XoB40pGxm.7p.rJB0B1C2uf90X//VMVA2XGh0nLOlyKdSmrLtkJNG','New Staff','staff','+6017238172931','2026-05-13 15:12:17.652','2026-05-13 15:12:17.652'),
    ('d2e3afa1-f3d1-4fa6-8e4e-57454d8262c8'::uuid,'newstaf','$2a$10$ozj6YTkUKQr49Odl.lWR2uS/KhTzKUZAHkpxk0RU93ZEZTB2AgVne','New Staff','manager','+60827261','2026-05-18 01:45:19.421231','2026-05-18 01:45:19.421231'),
    ('b5321608-7666-4f10-9991-5bbb55d094d5'::uuid,'barb123','$2a$10$7jy2aay/C1RCwN66oys9uOaRwflCtnsVt.rLw9w774jz1JAzm2Qwq','Barbershop','user','+60172652627','2026-05-13 15:32:40.823','2026-05-18 11:37:18.298104'),
    ('5976f3f4-ac2e-408a-834f-1c3f4ab6c4d7'::uuid,'staff1','$2a$10$RzM1y8b.fSUB17kDx3ZUIuc4nRgVB/L0U6I5hEnPT.291/0eyCmE.','Staff1New','staff','+60172652527','2026-05-13 14:39:54.371','2026-05-18 11:44:35.2449'),
    ('56aaa010-c314-49bd-a3c9-d70895afca78'::uuid,'janedoe','$2a$10$J3NXnyPdWWX.UNtCkznXhey.3dEjbJ.VVrodsLJ3Iw1tm1JdqPtjK','Jane Doe','customer','+6017283721837','2026-05-18 11:59:47.032394','2026-05-18 11:59:47.032394'),
    ('58a67498-059c-4414-b094-cc6ed34ac6ba'::uuid,'admin1','$2a$12$K4c..dtUuHSpCLdkUdz0fu7RI5JSStuY2MBlfzojk.tPQh/HST.iS','Admin 1','admin','+60172652627','2026-05-13 14:40:19.613','2026-05-13 14:40:19.613');
