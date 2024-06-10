CREATE SCHEMA login;
USE login;

CREATE TABLE cinema_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    dni VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(255),
    city VARCHAR(100)
);
