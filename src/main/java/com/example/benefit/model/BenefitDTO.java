package com.example.benefit.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BenefitDTO {
    private Long id;
    private String name;

    public BenefitDTO(BenefitDTOBuilder benefitBuilder) {
        this.id = benefitBuilder.id;
        this.name = benefitBuilder.name;
    }

}