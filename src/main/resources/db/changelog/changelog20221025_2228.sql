-- liquibase formatted sql

-- changeset nika.avalishvili:1
CREATE TABLE benefit_type (id SERIAL PRIMARY KEY,
                        name VARCHAR(255))

-- changeset nika.avalishvili:2
CREATE TABLE calculation_method (id SERIAL PRIMARY KEY,
                        name VARCHAR(255))