package com.example.benefit.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BenefitMapper {

    public BenefitDTO entityToDto(Benefit benefit){
        return BenefitDTO.builder()
                .id(benefit.getId())
                .name(benefit.getName())
                .benefitType(benefit.getBenefitType())
                .calculationMethod(benefit.getCalculationMethod())
                .build();
    }

    public List<BenefitDTO> entityToDto(List<Benefit> benefits){
        return benefits.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Benefit dtoToEntity(BenefitDTO benefitDTO){
        return Benefit.builder()
                .id(benefitDTO.getId())
                .name(benefitDTO.getName())
                .benefitType(benefitDTO.getBenefitType())
                .calculationMethod(benefitDTO.getCalculationMethod())
                .build();
    }

    public List<Benefit> dtoToEntity(List<BenefitDTO> benefitDTOs){
        return benefitDTOs.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
