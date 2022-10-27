package com.example.benefit.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CalculationMethodMapper {

    public CalculationMethod dtoToEntity(CalculationMethodDTO calculationMethodDTO) {
        return CalculationMethod.builder()
                .methodId(calculationMethodDTO.getMethodId())
                .name(calculationMethodDTO.getName())
                .build();
    }

    public List<CalculationMethod> dtoToEntity(List<CalculationMethodDTO> calculationMethodDTOS) {
        return calculationMethodDTOS.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }


    public CalculationMethodDTO entityToDto(CalculationMethod calculationMethod) {
        return CalculationMethodDTO.builder()
                .methodId(calculationMethod.getMethodId())
                .name(calculationMethod.getName())
                .build();
    }

    public List<CalculationMethodDTO> entityToDto(List<CalculationMethod> calculationMethods) {
        return calculationMethods.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
