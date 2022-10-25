package com.example.benefit.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Benefit(Benefit.BenefitBuilder benefitBuilder) {
        this.id = benefitBuilder.id;
        this.name = benefitBuilder.name;
    }

}
