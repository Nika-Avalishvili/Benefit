-- liquibase formatted sql

-- changeset nika.avalishvili:1
CREATE TABLE benefit (id SERIAL PRIMARY KEY,
                        name VARCHAR(255),
                        type_id INT,
                        method_id INT)

