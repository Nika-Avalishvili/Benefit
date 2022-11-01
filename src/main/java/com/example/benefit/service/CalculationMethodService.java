package com.example.benefit.service;

import com.example.benefit.model.CalculationMethod;
import com.example.benefit.model.CalculationMethodDTO;
import com.example.benefit.model.CalculationMethodMapper;
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
        if (calculationMethodRepository.findByName(calculationMethod.getName()) == null) {
            calculationMethodRepository.save(calculationMethod);
            return calculationMethodMapper.entityToDto(calculationMethod);
        } else {
            return calculationMethodMapper.entityToDto(calculationMethodRepository.findByName(calculationMethod.getName()));
        }
    }

    public void deleteCalculationMethod(Long id) {
        calculationMethodRepository.deleteById(id);
    }

    public List<CalculationMethodDTO> getAllCalculationMethods() {
        List<CalculationMethod> calculationMethods = calculationMethodRepository.findAll();
        return calculationMethodMapper.entityToDto(calculationMethods);
    }

    public CalculationMethodDTO getCalculationMethodById(Long id) {
        CalculationMethod calculationMethod = calculationMethodRepository.findById(id).orElseThrow();
        return calculationMethodMapper.entityToDto(calculationMethod);
    }
}
