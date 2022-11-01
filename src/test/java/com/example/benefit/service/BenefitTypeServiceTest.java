package com.example.benefit.service;

import com.example.benefit.model.BenefitType;
import com.example.benefit.model.BenefitTypeDTO;
import com.example.benefit.model.BenefitTypeMapper;
import com.example.benefit.repository.BenefitTypeRepository;
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
public class BenefitTypeServiceTest {

    @Mock
    BenefitTypeRepository benefitTypeRepository;
    BenefitTypeMapper benefitTypeMapper;
    BenefitTypeService benefitTypeService;

    @BeforeEach
    void setUp() {
        benefitTypeMapper = new BenefitTypeMapper();
        benefitTypeService = new BenefitTypeService(benefitTypeMapper, benefitTypeRepository);
    }

    @Test
    void createAndUpdateBenefitType() {
        BenefitTypeDTO benefitTypeDTO = new BenefitTypeDTO(1L, "Salary");
        Mockito.when(benefitTypeRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        BenefitTypeDTO actualDTO = benefitTypeService.createAndUpdateBenefitType(benefitTypeDTO);

        Assertions.assertEquals(benefitTypeDTO, actualDTO);
    }

    @Test
    void findAllBenefitTypes() {
        BenefitType benefitType1 = new BenefitType(5L, "ABCD Award");
        BenefitType benefitType2 = new BenefitType(6L, "Dividends equivalent payment");
        Mockito.when(benefitTypeRepository.findAll()).thenReturn(List.of(benefitType1, benefitType2));


        Assertions.assertEquals(2, benefitTypeService.getAllBenefitType().size());
    }

    @Test
    void findBenefitTypeById() {
        BenefitType benefitType1 = new BenefitType(5L, "ABCD Award");
        BenefitType benefitType2 = new BenefitType(6L, "Dividends equivalent payment");
        Mockito.when(benefitTypeRepository.findById(anyLong())).thenAnswer(invocationOnMock -> Stream.of(benefitType1, benefitType2).filter(e -> e.getTypeId().equals(invocationOnMock.getArgument(0))).findFirst());

        Assertions.assertEquals("ABCD Award", benefitTypeService.getBenefitTypeById(5L).getName());
        Assertions.assertEquals("Dividends equivalent payment", benefitTypeService.getBenefitTypeById(6L).getName());
    }

    @Test
    void deleteBenefitType() {
        BenefitType benefitType1 = new BenefitType(5L, "ABCD Award");
        benefitTypeService.deleteBenefitType(5L);
        Mockito.verify(benefitTypeRepository, times(1)).deleteById(5L);
    }

}
