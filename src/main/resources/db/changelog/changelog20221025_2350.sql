-- liquibase formatted sql

-- changeset nika.avalishvili:1
ALTER TABLE benefit
    ADD CONSTRAINT fk_benefit_type
          FOREIGN KEY (type_id)
            REFERENCES benefit_type(type_id)
            ON DELETE SET NULL

-- changeset nika.avalishvili:2
ALTER TABLE benefit
    ADD CONSTRAINT fk_calculation_method
          FOREIGN KEY (method_id)
            REFERENCES calculation_method(method_id)
            ON DELETE SET NULL

