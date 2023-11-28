CREATE TABLE users (
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(64) UNIQUE NOT NULL,
    password   VARCHAR(2048) NOT NULL,
    role       VARCHAR(32),
    status     VARCHAR(32),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);