package com.example.benefit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BenefitDTOForRabbitMQ {
    private Long id;
    private String name;
    private String benefitTypeName;
    private String calculationMethodName;
}
