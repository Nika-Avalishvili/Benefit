package com.example.benefit.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BenefitDTO {
    private Long id;
    private String name;
    private BenefitType benefitType;
    private CalculationMethod calculationMethod;
}
