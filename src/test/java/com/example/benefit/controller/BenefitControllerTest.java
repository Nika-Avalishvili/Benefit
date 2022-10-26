package com.example.benefit.controller;

import com.example.benefit.model.*;
import com.example.benefit.repository.BenefitRepository;
import com.example.benefit.service.BenefitService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class BenefitControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BenefitService benefitService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BenefitRepository benefitRepository;

    @BeforeEach
    void cleanUp() {
        benefitRepository.deleteAll();
    }

    @Test
    void getAllBenefits() throws Exception {
        BenefitType benefitType = BenefitType.builder()
                .name("Accrual")
                .build();

        CalculationMethod calculationMethod = CalculationMethod.builder()
                .name("Gross")
                .build();

        BenefitDTO benefitDTO = BenefitDTO.builder()
                .name("Salary")
                .benefitType(benefitType)
                .calculationMethod(calculationMethod)
                .build();

        benefitService.createAndUpdateBenefit(benefitDTO);
        List<BenefitDTO> expectedBenefitDTOList = List.of(benefitDTO);

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.get("/benefit"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<BenefitDTO> actualBenefitDTOList = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        Assertions.assertEquals(1, expectedBenefitDTOList.size());
        Assertions.assertEquals(1, actualBenefitDTOList.size());

        BenefitDTO expectedBenefitDto = expectedBenefitDTOList.stream().findFirst().orElseThrow();
        BenefitDTO actualBenefitDto = actualBenefitDTOList.stream().findFirst().orElseThrow();

        assertThat(expectedBenefitDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(actualBenefitDto);

        Assertions.assertEquals(expectedBenefitDto.getBenefitType().getName(),actualBenefitDto.getBenefitType().getName());
        Assertions.assertEquals(expectedBenefitDto.getCalculationMethod().getName(),actualBenefitDto.getCalculationMethod().getName());

    }


    @Test
    void getBenefitById() throws Exception {
        BenefitType benefitType = BenefitType.builder()
                .name("Deduction")
                .build();

        CalculationMethod calculationMethod = CalculationMethod.builder()
                .name("Net")
                .build();

        BenefitDTO benefitDTO = BenefitDTO.builder()
                .name("Salary")
                .benefitType(benefitType)
                .calculationMethod(calculationMethod)
                .build();

        Long id = benefitService.createAndUpdateBenefit(benefitDTO).getId();

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.get("/benefit/{id}", id))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        BenefitDTO actualBenefitDTO = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        assertThat(actualBenefitDTO)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(benefitDTO);

        Assertions.assertEquals(benefitDTO.getBenefitType().getName(),actualBenefitDTO.getBenefitType().getName());
        Assertions.assertEquals(benefitDTO.getCalculationMethod().getName(),actualBenefitDTO.getCalculationMethod().getName());

    }

    @Test
    void addBenefit() throws Exception {
        BenefitType benefitType = BenefitType.builder()
                .name("Deduction")
                .build();

        CalculationMethod calculationMethod = CalculationMethod.builder()
                .name("Net")
                .build();

        BenefitDTO benefitDTO = BenefitDTO.builder()
                .name("Salary")
                .benefitType(benefitType)
                .calculationMethod(calculationMethod)
                .build();

        String requestJson = objectMapper.writeValueAsString(benefitDTO);

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.post("/benefit")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        BenefitDTO actualBenefitDTO = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        assertThat(actualBenefitDTO)
                .usingRecursiveComparison()
                .ignoringFields("id", "benefitType.id","calculationMethod.id")
                .isEqualTo(benefitDTO);

        Assertions.assertEquals(benefitDTO.getBenefitType().getName(),actualBenefitDTO.getBenefitType().getName());
        Assertions.assertEquals(benefitDTO.getCalculationMethod().getName(),actualBenefitDTO.getCalculationMethod().getName());
    }

    @Test
    void updateBenefit() throws Exception {
        BenefitType benefitType = BenefitType.builder()
                .name("Deduction")
                .build();

        CalculationMethod calculationMethod = CalculationMethod.builder()
                .name("Net")
                .build();

        BenefitDTO benefitDTO = BenefitDTO.builder()
                .name("Salary")
                .benefitType(benefitType)
                .calculationMethod(calculationMethod)
                .build();

        benefitService.createAndUpdateBenefit(benefitDTO);

        benefitDTO.setName("Bonus");

        String requestJson = objectMapper.writeValueAsString(benefitDTO);

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.put("/benefit")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        BenefitDTO actualBenefitDTO = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        assertThat(actualBenefitDTO)
                .usingRecursiveComparison()
                .ignoringFields("id", "benefitType.id","calculationMethod.id")
                .isEqualTo(benefitDTO);

        Assertions.assertEquals(benefitDTO.getBenefitType().getName(),actualBenefitDTO.getBenefitType().getName());
        Assertions.assertEquals(benefitDTO.getCalculationMethod().getName(),actualBenefitDTO.getCalculationMethod().getName());
    }


    @Test
    void deleteBenefit() throws Exception {
        BenefitType benefitType1 = BenefitType.builder()
                .name("Accrual")
                .build();

        BenefitType benefitType2 = BenefitType.builder()
                .name("Deduction")
                .build();

        CalculationMethod calculationMethod1 = CalculationMethod.builder()
                .name("Gross")
                .build();

        CalculationMethod calculationMethod2 = CalculationMethod.builder()
                .name("Net")
                .build();

        BenefitDTO benefitDTO1 = BenefitDTO.builder()
                .name("Meal allowance")
                .benefitType(benefitType1)
                .calculationMethod(calculationMethod1)
                .build();
        BenefitDTO benefitDTO2 = BenefitDTO.builder()
                .name("Car allowance")
                .benefitType(benefitType2)
                .calculationMethod(calculationMethod2)
                .build();

        Long firstId = benefitService.createAndUpdateBenefit(benefitDTO1).getId();
        benefitService.createAndUpdateBenefit(benefitDTO2);

        String firstResponseAsAString = mockMvc.perform(MockMvcRequestBuilders.delete("/benefit?id={id}", firstId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String lastResponseAsAString = mockMvc.perform(MockMvcRequestBuilders.get("/benefit"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<BenefitDTO> actualBenefitDTOList = objectMapper.readValue(lastResponseAsAString, new TypeReference<>() {
        });

        assertThat(actualBenefitDTOList)
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .doesNotContain(benefitDTO1);

        BenefitDTO actualBenefitDTO = actualBenefitDTOList.stream().findFirst().orElseThrow();

        assertThat(actualBenefitDTO)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(benefitDTO2);

        Assertions.assertEquals(benefitDTO2.getBenefitType().getName(),actualBenefitDTO.getBenefitType().getName());
        Assertions.assertEquals(benefitDTO2.getCalculationMethod().getName(),actualBenefitDTO.getCalculationMethod().getName());

    }

}
