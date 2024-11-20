-- Файл выполняется при старте контейнера
--
CREATE DATABASE IF NOT EXISTS contacts;

-- Создание схемы contacts_schema
CREATE SCHEMA IF NOT EXISTS contacts_schema;

-- Создание таблицы contact в схеме contacts_schema
CREATE TABLE IF NOT EXISTS contact
(
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL
);


