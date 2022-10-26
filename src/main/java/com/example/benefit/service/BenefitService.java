package com.example.benefit.service;

import com.example.benefit.model.Benefit;
import com.example.benefit.model.BenefitDTO;
import com.example.benefit.model.BenefitMapper;
import com.example.benefit.repository.BenefitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BenefitService {

    private final BenefitRepository benefitRepository;
    private final BenefitMapper benefitMapper;

    public BenefitDTO createAndUpdateBenefit(BenefitDTO benefitDTO) {
        Benefit benefit = benefitMapper.dtoToEntity(benefitDTO);
        benefitRepository.save(benefit);
        return benefitMapper.entityToDto(benefit);
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
