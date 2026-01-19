INSERT INTO users (id, name, username, encrypted_password, created_at, updated_at) VALUES
(1, 'Иван Иванов', 'ivan', '$2a$12$ANoj2qMISLbH4VGozvtnS.ky6rSpFSw/kiGHO/n8UMgAEcAX4V5mm', NOW(), NOW()), -- password111
(2, 'Мария Петрова', 'maria', '$2a$12$YXizmWLGGT0UdbPi1wVB/Ow4S0MHlAMIOyZ8EmDY5kEwdBB.IQuGi', NOW(), NOW()); -- password222

INSERT INTO user_roles (user_id, role) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

INSERT INTO accounts (id, user_id, currency, balance, created_at, updated_at) VALUES
(1, 1, 'RUB', 10000.00, NOW(), NOW()),
(2, 1, 'USD', 2500.50, NOW(), NOW()),
(3, 2, 'EUR', 5000.00, NOW(), NOW());

INSERT INTO cards (id, account_id, pan_encrypted, pan_hash, pan_mask, expiration_date, status, created_at, updated_at) VALUES
(1, 1, 'Zdbl4rV2TFLrL5bk6W9fkJBZbGhCzUv0M918fLRxmAk=', 'ASpioODrdvdlRT3EUl6pd/AaQ0D0DGOsL0fEtL4OoI4=', '**** **** **** 1111', '2026-12', 'STATUS_ACTIVE', NOW(), NOW()), -- 1111 1111 1111 1111
(2, 1, '2tEkmy+dA2ImnveQiCsznCELKj8Wl+Hy/Ijq5Rs+Prk=', 'Amh2ykehYYtA/mkp7vUCXtJUMIeaapp+40EpTr7vqao=', '**** **** **** 2222', '2025-06', 'STATUS_BLOCKED', NOW(), NOW()), -- 2222 2222 2222 2222
(3, 2, 'uEkMJJqyop6xwzUoAESOPpCU9fJrTqz62m+U+zPEd9A=', 'Ha4zUOiHHi7iLzihWJV3qDdH3ZKlXaqhtmnwxuwJ7pk=', '**** **** **** 3333', '2027-03', 'STATUS_ACTIVE', NOW(), NOW()), -- 3333 3333 3333 3333
(4, 3, 'Ol5LxnjGsb2t9AcIdIw97vpuCAUtl16YdAfhbwvyejA=', 'swIYPJFXiT/0U236pxlMGl7ND0cAZ7Fz2G8ak5XnZCc=', '**** **** **** 4444', '2024-09', 'STATUS_EXPIRED', NOW(), NOW()); -- 4444 4444 4444 4444

INSERT INTO transfers (id, src_card_id, dest_card_id, amount, status, created_at, updated_at) VALUES
(UUID_TO_BIN(UUID()), 1, 3, 1500.00, 'COMPLETED', NOW(), NOW()),
(UUID_TO_BIN(UUID()), 2, 3, 500.50, 'COMPLETED', NOW(), NOW()),
(UUID_TO_BIN(UUID()), 3, 1, 200.00, 'FAILED', NOW(), NOW()),
(UUID_TO_BIN(UUID()), 3, 4, 300.00, 'FAILED', NOW(), NOW());

