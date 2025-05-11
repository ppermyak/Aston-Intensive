CREATE TABLE IF NOT EXISTS users (
    id              BIGSERIAL           PRIMARY KEY,
    name            VARCHAR(255)        NOT NULL,
    email           VARCHAR(255)        NOT NULL            UNIQUE,
    age             INTEGER             NOT NULL,
    created_at      TIMESTAMP
)