-- liquibase formatted sql

-- changeset nika.avalishvili:1
ALTER TABLE benefit
    ADD COLUMN type_id INT
    REFERENCES benefit_type
    ON DELETE SET NULL

-- changeset nika.avalishvili:2
ALTER TABLE benefit
    ADD COLUMN method_id INT
    REFERENCES calculation_method
    ON DELETE SET NULL

