-- liquibase formatted sql

-- changeset nika.avalishvili:1
ALTER TABLE benefit
ADD COLUMN "fk_ben_type_id" INT,
ADD COLUMN "fk_calc_method_id" INT;
