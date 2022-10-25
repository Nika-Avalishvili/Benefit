package com.example.benefit.service;

import com.example.benefit.model.Benefit;
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

    @BeforeEach
    void setUp() {
        benefitMapper = new BenefitMapper();
        benefitService = new BenefitService(benefitRepository, benefitMapper);
    }

    @Test
    void createAndUpdateBenefit() {
        BenefitDTO benefitDTO = new BenefitDTO(1L, "Salary");
        Mockito.when(benefitRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        BenefitDTO actualDTO = benefitService.createAndUpdateBenefit(benefitDTO);

        Assertions.assertEquals(benefitDTO, actualDTO);
    }

    @Test
    void findAllBenefits() {
        Benefit benefit1 = new Benefit(5L, "ABCD Award");
        Benefit benefit2 = new Benefit(6L, "Dividends equivalent payment");
        Mockito.when(benefitRepository.findAll()).thenReturn(List.of(benefit1, benefit2));


        Assertions.assertEquals(2, benefitService.getAllBenefit().size());
    }

    @Test
    void findEmployeeById() {
        Benefit benefit1 = new Benefit(5L, "ABCD Award");
        Benefit benefit2 = new Benefit(6L, "Dividends equivalent payment");
        Mockito.when(benefitRepository.findById(anyLong())).thenAnswer(invocationOnMock -> Stream.of(benefit1, benefit2).filter(e -> e.getId().equals(invocationOnMock.getArgument(0))).findFirst());

        Assertions.assertEquals("ABCD Award", benefitService.getBenefitById(5L).getName());
        Assertions.assertEquals("Dividends equivalent payment", benefitService.getBenefitById(6L).getName());
    }

    @Test
    void deleteEmployee() {
        Benefit benefit1 = new Benefit(5L, "ABCD Award");
        benefitService.deleteBenefit(5L);
        Mockito.verify(benefitRepository, times(1)).deleteById(5L);
    }

}
