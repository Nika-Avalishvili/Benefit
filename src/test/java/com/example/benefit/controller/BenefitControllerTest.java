package com.example.benefit.controller;

import com.example.benefit.model.BenefitDTO;
import com.example.benefit.model.BenefitTypeDTO;
import com.example.benefit.model.CalculationMethodDTO;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.function.StreamBridge;
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
    @MockBean
    StreamBridge streamBridge;
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
        BenefitTypeDTO benefitTypeDTO = BenefitTypeDTO.builder()
                .name("Accrual")
                .build();

        CalculationMethodDTO calculationMethodDTO = CalculationMethodDTO.builder()
                .name("Gross")
                .build();

        BenefitDTO benefitDTO = BenefitDTO.builder()
                .name("Salary")
                .benefitTypeDTO(benefitTypeDTO)
                .calculationMethodDTO(calculationMethodDTO)
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
                .ignoringFields("id", "benefitTypeDTO.typeId", "calculationMethodDTO.methodId")
                .isEqualTo(actualBenefitDto);

        Assertions.assertEquals(expectedBenefitDto.getBenefitTypeDTO().getName(), actualBenefitDto.getBenefitTypeDTO().getName());
        Assertions.assertEquals(expectedBenefitDto.getCalculationMethodDTO().getName(), actualBenefitDto.getCalculationMethodDTO().getName());

    }


    @Test
    void getBenefitById() throws Exception {
        BenefitTypeDTO benefitTypeDTO = BenefitTypeDTO.builder()
                .name("Deduction")
                .build();

        CalculationMethodDTO calculationMethodDTO = CalculationMethodDTO.builder()
                .name("Net")
                .build();

        BenefitDTO benefitDTO = BenefitDTO.builder()
                .name("Salary")
                .benefitTypeDTO(benefitTypeDTO)
                .calculationMethodDTO(calculationMethodDTO)
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
                .ignoringFields("id", "benefitTypeDTO.typeId", "calculationMethodDTO.methodId")
                .isEqualTo(benefitDTO);

        Assertions.assertEquals(benefitDTO.getBenefitTypeDTO().getName(), actualBenefitDTO.getBenefitTypeDTO().getName());
        Assertions.assertEquals(benefitDTO.getCalculationMethodDTO().getName(), actualBenefitDTO.getCalculationMethodDTO().getName());

    }

    @Test
    void getBenefitByIdNotFoundTest() throws Exception {
        BenefitTypeDTO benefitTypeDTO = BenefitTypeDTO.builder()
                .name("Deduction")
                .build();

        CalculationMethodDTO calculationMethodDTO = CalculationMethodDTO.builder()
                .name("Net")
                .build();

        BenefitDTO benefitDTO = BenefitDTO.builder()
                .name("Salary")
                .benefitTypeDTO(benefitTypeDTO)
                .calculationMethodDTO(calculationMethodDTO)
                .build();

        Long id = benefitService.createAndUpdateBenefit(benefitDTO).getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/benefit/{id}", id))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/benefit/{id}", 1500L))
                .andExpect(status().is(404));

    }

    @Test
    void addBenefit() throws Exception {
        BenefitTypeDTO benefitTypeDTO = BenefitTypeDTO.builder()
                .name("Deduction")
                .build();

        CalculationMethodDTO calculationMethodDTO = CalculationMethodDTO.builder()
                .name("Net")
                .build();

        BenefitDTO benefitDTO = BenefitDTO.builder()
                .name("Salary")
                .benefitTypeDTO(benefitTypeDTO)
                .calculationMethodDTO(calculationMethodDTO)
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
                .ignoringFields("id", "benefitTypeDTO.typeId", "calculationMethodDTO.methodId")
                .isEqualTo(benefitDTO);

        Assertions.assertEquals(benefitDTO.getBenefitTypeDTO().getName(), actualBenefitDTO.getBenefitTypeDTO().getName());
        Assertions.assertEquals(benefitDTO.getCalculationMethodDTO().getName(), actualBenefitDTO.getCalculationMethodDTO().getName());
    }

    @Test
    void updateBenefit() throws Exception {
        BenefitTypeDTO benefitTypeDTO = BenefitTypeDTO.builder()
                .name("Deduction")
                .build();

        CalculationMethodDTO calculationMethodDTO = CalculationMethodDTO.builder()
                .name("Net")
                .build();

        BenefitDTO benefitDTO = BenefitDTO.builder()
                .name("Salary")
                .benefitTypeDTO(benefitTypeDTO)
                .calculationMethodDTO(calculationMethodDTO)
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
                .ignoringFields("id", "benefitTypeDTO.typeId", "calculationMethodDTO.methodId")
                .isEqualTo(benefitDTO);

        Assertions.assertEquals(benefitDTO.getBenefitTypeDTO().getName(), actualBenefitDTO.getBenefitTypeDTO().getName());
        Assertions.assertEquals(benefitDTO.getCalculationMethodDTO().getName(), actualBenefitDTO.getCalculationMethodDTO().getName());
    }


    @Test
    void deleteBenefit() throws Exception {
        BenefitTypeDTO benefitTypeDTO1 = BenefitTypeDTO.builder()
                .name("Accrual")
                .build();

        BenefitTypeDTO benefitTypeDTO2 = BenefitTypeDTO.builder()
                .name("Deduction")
                .build();

        CalculationMethodDTO calculationMethodDTO1 = CalculationMethodDTO.builder()
                .name("Gross")
                .build();

        CalculationMethodDTO calculationMethodDTO2 = CalculationMethodDTO.builder()
                .name("Net")
                .build();

        BenefitDTO benefitDTO1 = BenefitDTO.builder()
                .name("Meal allowance")
                .benefitTypeDTO(benefitTypeDTO1)
                .calculationMethodDTO(calculationMethodDTO1)
                .build();
        BenefitDTO benefitDTO2 = BenefitDTO.builder()
                .name("Car allowance")
                .benefitTypeDTO(benefitTypeDTO2)
                .calculationMethodDTO(calculationMethodDTO2)
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
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "benefitTypeDTO.typeId", "calculationMethodDTO.methodId")
                .doesNotContain(benefitDTO1);

        BenefitDTO actualBenefitDTO = actualBenefitDTOList.stream().findFirst().orElseThrow();

        assertThat(actualBenefitDTO)
                .usingRecursiveComparison()
                .ignoringFields("id", "benefitTypeDTO.typeId", "calculationMethodDTO.methodId")
                .isEqualTo(benefitDTO2);

        Assertions.assertEquals(benefitDTO2.getBenefitTypeDTO().getName(), actualBenefitDTO.getBenefitTypeDTO().getName());
        Assertions.assertEquals(benefitDTO2.getCalculationMethodDTO().getName(), actualBenefitDTO.getCalculationMethodDTO().getName());

    }

}
