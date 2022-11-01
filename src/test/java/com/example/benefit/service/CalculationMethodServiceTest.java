package com.example.benefit.service;

import com.example.benefit.model.CalculationMethod;
import com.example.benefit.model.CalculationMethodDTO;
import com.example.benefit.model.CalculationMethodMapper;
import com.example.benefit.repository.CalculationMethodRepository;
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
public class CalculationMethodServiceTest {

    @Mock
    CalculationMethodRepository calculationMethodRepository;
    CalculationMethodMapper calculationMethodMapper;
    CalculationMethodService calculationMethodService;

    @BeforeEach
    void setUp() {
        calculationMethodMapper = new CalculationMethodMapper();
        calculationMethodService = new CalculationMethodService(calculationMethodMapper, calculationMethodRepository);
    }

    @Test
    void createAndUpdateCalculationMethod() {
        CalculationMethodDTO calculationMethodDTO = new CalculationMethodDTO(1L, "Salary");
        Mockito.when(calculationMethodRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        CalculationMethodDTO actualDTO = calculationMethodService.createAndUpdateCalculationMethod(calculationMethodDTO);

        Assertions.assertEquals(calculationMethodDTO, actualDTO);
    }

    @Test
    void findAllCalculationMethods() {
        CalculationMethod calculationMethod1 = new CalculationMethod(5L, "ABCD Award");
        CalculationMethod calculationMethod2 = new CalculationMethod(6L, "Dividends equivalent payment");
        Mockito.when(calculationMethodRepository.findAll()).thenReturn(List.of(calculationMethod1, calculationMethod2));


        Assertions.assertEquals(2, calculationMethodService.getAllCalculationMethods().size());
    }

    @Test
    void findCalculationMethodById() {
        CalculationMethod calculationMethod1 = new CalculationMethod(5L, "ABCD Award");
        CalculationMethod calculationMethod2 = new CalculationMethod(6L, "Dividends equivalent payment");
        Mockito.when(calculationMethodRepository.findById(anyLong())).thenAnswer(invocationOnMock -> Stream.of(calculationMethod1, calculationMethod2).filter(e -> e.getMethodId().equals(invocationOnMock.getArgument(0))).findFirst());

        Assertions.assertEquals("ABCD Award", calculationMethodService.getCalculationMethodById(5L).getName());
        Assertions.assertEquals("Dividends equivalent payment", calculationMethodService.getCalculationMethodById(6L).getName());
    }

    @Test
    void deleteCalculationMethod() {
        CalculationMethod calculationMethod1 = new CalculationMethod(5L, "ABCD Award");
        calculationMethodService.deleteCalculationMethod(5L);
        Mockito.verify(calculationMethodRepository, times(1)).deleteById(5L);
    }

}
