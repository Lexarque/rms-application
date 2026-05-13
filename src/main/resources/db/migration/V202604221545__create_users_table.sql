CREATE TABLE IF NOT EXISTS users (
    id          UUID PRIMARY KEY,
    username    VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name   VARCHAR(255) NOT NULL,
    role        VARCHAR(50)  NOT NULL,
    phone_number VARCHAR(20),
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);