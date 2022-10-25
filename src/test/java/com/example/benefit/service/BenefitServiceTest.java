package com.example.benefit.service;

import com.example.benefit.model.BenefitDTO;
import com.example.benefit.model.BenefitMapper;
import com.example.benefit.repository.BenefitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BenefitServiceTest {

    @Mock
    BenefitRepository benefitRepository;
    BenefitMapper benefitMapper;
    BenefitService benefitService;

    @BeforeEach
    void setUp(){
        benefitMapper = new BenefitMapper();
        benefitService = new BenefitService(benefitRepository, benefitMapper);
    }

    @Test
    void createAndUpdateBenefit(){
        BenefitDTO benefitDTO = new BenefitDTO(1L, "Salary");
        Mockito.when(benefitRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        BenefitDTO actualDTO = benefitService.createAndUpdateBenefit(benefitDTO);

        Assertions.assertEquals(benefitDTO,actualDTO);
    }


}
