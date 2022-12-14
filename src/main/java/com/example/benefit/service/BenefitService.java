package com.example.benefit.service;

import com.example.benefit.exceptionHandler.BenefitNotFoundException;
import com.example.benefit.model.Benefit;
import com.example.benefit.model.BenefitDTO;
import com.example.benefit.model.BenefitDTOForMQ;
import com.example.benefit.model.BenefitMapper;
import com.example.benefit.repository.BenefitRepository;
import com.example.benefit.repository.BenefitTypeRepository;
import com.example.benefit.repository.CalculationMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BenefitService {

    private final BenefitRepository benefitRepository;
    private final BenefitMapper benefitMapper;
    private final BenefitTypeRepository benefitTypeRepository;
    private final CalculationMethodRepository calculationMethodRepository;
    private final StreamBridge streamBridge;

    public BenefitDTOForMQ convertDtoToMQ(BenefitDTO benefitDTO){
        return BenefitDTOForMQ.builder()
                .id(benefitDTO.getId())
                .name(benefitDTO.getName())
                .benefitTypeName(benefitDTO.getBenefitTypeDTO().getName())
                .calculationMethodName(benefitDTO.getCalculationMethodDTO().getName())
                .build();
    }

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

        Benefit savedBenefit = benefitRepository.save(benefit);
        BenefitDTO updatedBenefitDto = benefitMapper.entityToDto(savedBenefit);
        
        streamBridge.send("benefit-out-0", convertDtoToMQ(updatedBenefitDto));
        return updatedBenefitDto;
    }

    public void deleteBenefit(Long id) {
        benefitRepository.deleteById(id);
    }

    public List<BenefitDTO> getAllBenefit() {
        List<Benefit> benefits = benefitRepository.findAll();
        return benefitMapper.entityToDto(benefits);
    }

    public BenefitDTO getBenefitById(Long id) {
        Benefit benefit = benefitRepository.findById(id)
                .orElseThrow(BenefitNotFoundException::new);
        return benefitMapper.entityToDto(benefit);
    }
}
