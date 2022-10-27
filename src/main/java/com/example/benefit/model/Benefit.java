package com.example.benefit.model;

import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private BenefitType benefitType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "method_id")
    private CalculationMethod calculationMethod;

}
