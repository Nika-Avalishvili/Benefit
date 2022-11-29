package com.example.benefit.service;

import com.example.benefit.model.*;
import com.example.benefit.repository.BenefitRepository;
import com.example.benefit.repository.BenefitTypeRepository;
import com.example.benefit.repository.CalculationMethodRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class BenefitServiceTest {

    @Mock
    BenefitRepository benefitRepository;
    BenefitMapper benefitMapper;
    BenefitService benefitService;
    @Mock
    BenefitTypeRepository benefitTypeRepository;
    @Mock
    CalculationMethodRepository calculationMethodRepository;
    @Mock
    StreamBridge streamBridge;

    @BeforeEach
    void setUp() {
        benefitMapper = new BenefitMapper(new BenefitTypeMapper(), new CalculationMethodMapper());
        benefitService = new BenefitService(benefitRepository, benefitMapper, benefitTypeRepository, calculationMethodRepository, streamBridge);
    }

    @Test
    void createAndUpdateBenefit() {
        BenefitDTO benefitDTO = new BenefitDTO(1L, "Salary", new BenefitTypeDTO(1L, "Accrual"), new CalculationMethodDTO(2L, "Gross"));
        Mockito.when(benefitRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        BenefitDTO actualDTO = benefitService.createAndUpdateBenefit(benefitDTO);

        Assertions.assertEquals(benefitDTO, actualDTO);
        Assertions.assertEquals(benefitDTO.getBenefitTypeDTO(), actualDTO.getBenefitTypeDTO());
        Assertions.assertEquals("Accrual", actualDTO.getBenefitTypeDTO().getName());
        Assertions.assertEquals("Gross", actualDTO.getCalculationMethodDTO().getName());
    }

    @Test
    void findAllBenefits() {
        Benefit benefit1 = new Benefit(5L, "ABCD Award", new BenefitType(1L, "Deduction"), new CalculationMethod());
        Benefit benefit2 = new Benefit(6L, "Dividends equivalent payment", new BenefitType(), new CalculationMethod(1L, "Gross"));
        Mockito.when(benefitRepository.findAll()).thenReturn(List.of(benefit1, benefit2));


        Assertions.assertEquals(2, benefitService.getAllBenefit().size());
        Assertions.assertEquals(benefit1.getBenefitType().getName(), benefitService.getAllBenefit().stream().findFirst().get().getBenefitTypeDTO().getName());
        Assertions.assertEquals(benefit2.getCalculationMethod().getName(), benefitService.getAllBenefit().get(1).getCalculationMethodDTO().getName());
    }

    @Test
    void findBenefitById() {
        Benefit benefit1 = new Benefit(5L, "ABCD Award", new BenefitType(2L, "Accrual"), new CalculationMethod());
        Benefit benefit2 = new Benefit(6L, "Dividends equivalent payment", new BenefitType(), new CalculationMethod());
        Mockito.when(benefitRepository.findById(anyLong())).thenAnswer(invocationOnMock -> Stream.of(benefit1, benefit2).filter(e -> e.getId().equals(invocationOnMock.getArgument(0))).findFirst());

        Assertions.assertEquals("ABCD Award", benefitService.getBenefitById(5L).getName());
        Assertions.assertEquals("Dividends equivalent payment", benefitService.getBenefitById(6L).getName());
        Assertions.assertEquals(benefit1.getBenefitType().getName(), benefitService.getBenefitById(5L).getBenefitTypeDTO().getName());
    }

    @Test
    void deleteBenefit() {
        Benefit benefit1 = new Benefit(5L, "ABCD Award", new BenefitType(), new CalculationMethod());
        benefitService.deleteBenefit(5L);
        Mockito.verify(benefitRepository, times(1)).deleteById(5L);
    }

}
