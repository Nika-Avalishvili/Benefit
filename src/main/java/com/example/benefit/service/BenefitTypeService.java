package com.example.benefit.service;

import com.example.benefit.model.BenefitType;
import com.example.benefit.model.BenefitTypeDTO;
import com.example.benefit.model.BenefitTypeMapper;
import com.example.benefit.repository.BenefitTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BenefitTypeService {

    private final BenefitTypeMapper benefitTypeMapper;
    private final BenefitTypeRepository benefitTypeRepository;

    public BenefitTypeDTO createAndUpdateBenefitType(BenefitTypeDTO benefitTypeDTO) {
        BenefitType benefitType = benefitTypeMapper.dtoToEntity(benefitTypeDTO);
        if (benefitTypeRepository.findByName(benefitType.getName()) == null) {
            benefitTypeRepository.save(benefitType);
            return benefitTypeMapper.entityToDto(benefitType);
        } else {
            return benefitTypeMapper.entityToDto(benefitTypeRepository.findByName(benefitType.getName()));
        }
    }

    public void deleteBenefitType(Long id) {
        benefitTypeRepository.deleteById(id);
    }

    public List<BenefitTypeDTO> getAllBenefitType() {
        List<BenefitType> benefitTypes = benefitTypeRepository.findAll();
        return benefitTypeMapper.entityToDto(benefitTypes);
    }

    public BenefitTypeDTO getBenefitTypeById(Long id) {
        BenefitType benefitType = benefitTypeRepository.findById(id).orElseThrow();
        return benefitTypeMapper.entityToDto(benefitType);
    }
}
