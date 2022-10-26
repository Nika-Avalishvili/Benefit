package com.example.benefit.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BenefitMapper {

    private final BenefitTypeMapper benefitTypeMapper;
    private final CalculationMethodMapper calculationMethodMapper;

    public BenefitDTO entityToDto(Benefit benefit){
        return BenefitDTO.builder()
                .id(benefit.getId())
                .name(benefit.getName())
                .benefitTypeDTO(benefitTypeMapper.entityToDto(benefit.getBenefitType()))
                .calculationMethodDTO(calculationMethodMapper.entityToDto(benefit.getCalculationMethod()))
                .build();
    }

    public List<BenefitDTO> entityToDto(List<Benefit> benefits){
        return benefits.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Benefit dtoToEntity(BenefitDTO benefitDTO){
        return Benefit.builder()
                .id(benefitDTO.getId())
                .name(benefitDTO.getName())
                .benefitType(benefitTypeMapper.dtoToEntity(benefitDTO.getBenefitTypeDTO()))
                .calculationMethod(calculationMethodMapper.dtoToEntity(benefitDTO.getCalculationMethodDTO()))
                .build();
    }

    public List<Benefit> dtoToEntity(List<BenefitDTO> benefitDTOs){
        return benefitDTOs.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
