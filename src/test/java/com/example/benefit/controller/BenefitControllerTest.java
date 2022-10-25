package com.example.benefit.controller;

import com.example.benefit.model.BenefitDTO;
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
        BenefitDTO benefitDTO = BenefitDTO.builder().name("Salary").build();

        benefitService.createAndUpdateBenefit(benefitDTO);
        List<BenefitDTO> expecdtedBenefitDTOList = List.of(benefitDTO);

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.get("/benefit"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<BenefitDTO> actualBenefitDTOList = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        Assertions.assertEquals(1, expecdtedBenefitDTOList.size());
        Assertions.assertEquals(1, actualBenefitDTOList.size());

        BenefitDTO expectedBenefitDto = expecdtedBenefitDTOList.stream().findFirst().orElseThrow();
        BenefitDTO actualBenefitDto = actualBenefitDTOList.stream().findFirst().orElseThrow();

        assertThat(expectedBenefitDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(actualBenefitDto);
    }


    @Test
    void getBenefitById() throws Exception {
        BenefitDTO benefitDTO = BenefitDTO.builder().name("Salary").build();

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
    }

    @Test
    void addOrUpdateBenefit() throws Exception {
        BenefitDTO benefitDTO = BenefitDTO.builder()
                .name("Salary")
                .build();

        benefitService.createAndUpdateBenefit(benefitDTO);

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
                .ignoringFields("id")
                .isEqualTo(benefitDTO);
    }

    @Test
    void deleteBenefit() throws Exception {
        BenefitDTO benefitDTO1 = BenefitDTO.builder()
                .name("Meal allowance")
                .build();
        BenefitDTO benefitDTO2 = BenefitDTO.builder()
                .name("Car allowance")
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
    }

}
