package com.example.benefit.service;

import com.example.benefit.model.*;
import com.example.benefit.repository.CalculationMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculationMethodService {
    private final CalculationMethodMapper calculationMethodMapper;
    private final CalculationMethodRepository calculationMethodRepository;

    public CalculationMethodDTO createAndUpdateCalculationMethod(CalculationMethodDTO calculationMethodDTO) {
        CalculationMethod calculationMethod = calculationMethodMapper.dtoToEntity(calculationMethodDTO);
        calculationMethodRepository.save(calculationMethod);
        return calculationMethodMapper.entityToDto(calculationMethod);
    }

    public void deleteCalculationMethod(Long id){
        calculationMethodRepository.deleteById(id);
    }

    public List<CalculationMethodDTO> getAllCalculationMethods(){
        List<CalculationMethod> calculationMethods = calculationMethodRepository.findAll();
        return calculationMethodMapper.entityToDto(calculationMethods);
    }

    public CalculationMethodDTO getCalculationMethodById (Long id){
        CalculationMethod calculationMethod = calculationMethodRepository.findById(id).orElseThrow();
        return calculationMethodMapper.entityToDto(calculationMethod);
    }
}
