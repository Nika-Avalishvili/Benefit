-- liquibase formatted sql

-- changeset nika.avalishvili:1
CREATE TABLE benefit_type (type_id SERIAL PRIMARY KEY,
                        name VARCHAR(255))

-- changeset nika.avalishvili:2
CREATE TABLE calculation_method (method_id SERIAL PRIMARY KEY,
                        name VARCHAR(255))
