package com.example.benefit.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BenefitTypeMapper {

    public BenefitTypeDTO entityToDto(BenefitType benefitType) {
        return BenefitTypeDTO.builder()
                .typeId(benefitType.getTypeId())
                .name(benefitType.getName())
                .build();
    }

    public List<BenefitTypeDTO> entityToDto(List<BenefitType> benefitTypes) {
        return benefitTypes.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public BenefitType dtoToEntity(BenefitTypeDTO benefitTypeDTO) {
        return BenefitType.builder()
                .typeId(benefitTypeDTO.getTypeId())
                .name(benefitTypeDTO.getName())
                .build();
    }

    public List<BenefitType> dtoToEntity(List<BenefitTypeDTO> benefitTypeDTOS) {
        return benefitTypeDTOS.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
