CREATE TABLE IF NOT EXISTS files (
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    file_name  VARCHAR(1024),
    file_path  VARCHAR(2048),
    status     VARCHAR(32),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);