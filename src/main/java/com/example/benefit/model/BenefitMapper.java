package com.example.benefit.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BenefitMapper {

    public BenefitDTO entityToDto(Benefit benefit){
        return new BenefitDTO.BenefitDTOBuilder()
                .id(benefit.getId())
                .name(benefit.getName())
                .build();
    }

    public List<BenefitDTO> entityToDto(List<Benefit> benefits){
        return benefits.stream().map( x -> entityToDto(x)).collect(Collectors.toList());
    }

    public Benefit dtoToEntity(BenefitDTO benefitDTO){
        return new Benefit.BenefitBuilder()
                .id(benefitDTO.getId())
                .name(benefitDTO.getName())
                .build();
    }

    public List<Benefit> dtoToEntity(List<BenefitDTO> benefitDTOs){
        return benefitDTOs.stream().map( x -> dtoToEntity(x)).collect(Collectors.toList());
    }

}
