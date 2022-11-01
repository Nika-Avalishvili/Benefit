package com.example.benefit.service;

import com.example.benefit.model.Benefit;
import com.example.benefit.model.BenefitDTO;
import com.example.benefit.model.BenefitMapper;
import com.example.benefit.repository.BenefitRepository;
import com.example.benefit.repository.BenefitTypeRepository;
import com.example.benefit.repository.CalculationMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BenefitService {

    private final BenefitRepository benefitRepository;
    private final BenefitMapper benefitMapper;

    private final BenefitTypeRepository benefitTypeRepository;
    private final CalculationMethodRepository calculationMethodRepository;

    public BenefitDTO createAndUpdateBenefit(BenefitDTO benefitDTO) {
        Benefit benefit = benefitMapper.dtoToEntity(benefitDTO);

        String benefitTypeName = benefit.getBenefitType().getName();
        if (benefitTypeRepository.findByName(benefitTypeName) != null) {
            benefit.setBenefitType(benefitTypeRepository.findByName(benefitTypeName));
        }

        String calculationMethodName = benefit.getCalculationMethod().getName();
        if (calculationMethodRepository.findByName(calculationMethodName) != null) {
            benefit.setCalculationMethod(calculationMethodRepository.findByName(calculationMethodName));
        }

        if (benefitRepository.findByName(benefit.getName()) == null) {
            benefitRepository.save(benefit);
            return benefitMapper.entityToDto(benefit);
        } else {
            return benefitMapper.entityToDto(benefitRepository.findByName(benefit.getName()));
        }
    }

    public void deleteBenefit(Long id) {
        benefitRepository.deleteById(id);
    }

    public List<BenefitDTO> getAllBenefit() {
        List<Benefit> benefits = benefitRepository.findAll();
        return benefitMapper.entityToDto(benefits);
    }

    public BenefitDTO getBenefitById(Long id) {
        Benefit benefit = benefitRepository.findById(id).orElseThrow();
        return benefitMapper.entityToDto(benefit);
    }
}
