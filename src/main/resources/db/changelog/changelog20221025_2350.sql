-- liquibase formatted sql

-- changeset nika.avalishvili:1
ALTER TABLE benefit
    ADD CONSTRAINT fk_ben_type_id
          FOREIGN KEY (id)
            REFERENCES benefit_type(id)

-- changeset nika.avalishvili:2
ALTER TABLE benefit
    ADD CONSTRAINT fk_calc_method_id
          FOREIGN KEY (id)
            REFERENCES calculation_method(id)
