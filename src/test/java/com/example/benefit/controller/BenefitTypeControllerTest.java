package com.example.benefit.controller;

import com.example.benefit.model.BenefitTypeDTO;
import com.example.benefit.repository.BenefitTypeRepository;
import com.example.benefit.service.BenefitTypeService;
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
public class BenefitTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BenefitTypeService benefitTypeService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BenefitTypeRepository benefitTypeRepository;

    @BeforeEach
    void cleanUp() {
        benefitTypeRepository.deleteAll();
    }

    @Test
    void getAllBenefitTypes() throws Exception {
        BenefitTypeDTO benefitTypeDTO = BenefitTypeDTO.builder().name("Salary").build();

        benefitTypeService.createAndUpdateBenefitType(benefitTypeDTO);
        List<BenefitTypeDTO> expectedBenefitTypeDTOList = List.of(benefitTypeDTO);

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.get("/benefitType"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<BenefitTypeDTO> actualBenefitTypeDTOList = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        Assertions.assertEquals(1, expectedBenefitTypeDTOList.size());
        Assertions.assertEquals(1, actualBenefitTypeDTOList.size());

        BenefitTypeDTO expectedBenefitTypeDto = expectedBenefitTypeDTOList.stream().findFirst().orElseThrow();
        BenefitTypeDTO actualBenefitTypeDto = actualBenefitTypeDTOList.stream().findFirst().orElseThrow();

        assertThat(expectedBenefitTypeDto)
                .usingRecursiveComparison()
                .ignoringFields("typeId")
                .isEqualTo(actualBenefitTypeDto);
    }


    @Test
    void getBenefitTypeById() throws Exception {
        BenefitTypeDTO benefitTypeDTO = BenefitTypeDTO.builder().name("Salary").build();

        Long id = benefitTypeService.createAndUpdateBenefitType(benefitTypeDTO).getTypeId();

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.get("/benefitType/{id}", id))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        BenefitTypeDTO actualBenefitTypeDTO = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        assertThat(actualBenefitTypeDTO)
                .usingRecursiveComparison()
                .ignoringFields("typeId")
                .isEqualTo(benefitTypeDTO);
    }

    @Test
    void addBenefitType() throws Exception {
        BenefitTypeDTO benefitTypeDTO = BenefitTypeDTO.builder()
                .name("Salary")
                .build();

        String requestJson = objectMapper.writeValueAsString(benefitTypeDTO);

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.post("/benefitType")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        BenefitTypeDTO actualBenefitTypeDTO = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        assertThat(actualBenefitTypeDTO)
                .usingRecursiveComparison()
                .ignoringFields("typeId")
                .isEqualTo(benefitTypeDTO);
    }

    @Test
    void updateBenefitType() throws Exception {
        BenefitTypeDTO benefitTypeDTO = BenefitTypeDTO.builder()
                .name("Salary")
                .build();

        benefitTypeService.createAndUpdateBenefitType(benefitTypeDTO);

        String requestJson = objectMapper.writeValueAsString(benefitTypeDTO);

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.put("/benefitType")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        BenefitTypeDTO actualBenefitTypeDTO = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        assertThat(actualBenefitTypeDTO)
                .usingRecursiveComparison()
                .ignoringFields("typeId")
                .isEqualTo(benefitTypeDTO);
    }

    @Test
    void deleteBenefitType() throws Exception {
        BenefitTypeDTO benefitTypeDTO1 = BenefitTypeDTO.builder()
                .name("Meal allowance")
                .build();
        BenefitTypeDTO benefitTypeDTO2 = BenefitTypeDTO.builder()
                .name("Car allowance")
                .build();

        Long firstId = benefitTypeService.createAndUpdateBenefitType(benefitTypeDTO1).getTypeId();
        benefitTypeService.createAndUpdateBenefitType(benefitTypeDTO2);

        String firstResponseAsAString = mockMvc.perform(MockMvcRequestBuilders.delete("/benefitType?id={id}", firstId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String lastResponseAsAString = mockMvc.perform(MockMvcRequestBuilders.get("/benefitType"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<BenefitTypeDTO> actualBenefitTypeDTOList = objectMapper.readValue(lastResponseAsAString, new TypeReference<>() {
        });

        assertThat(actualBenefitTypeDTOList)
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("typeId")
                .doesNotContain(benefitTypeDTO1);

        BenefitTypeDTO actualBenefitTypeDTO = actualBenefitTypeDTOList.stream().findFirst().orElseThrow();

        assertThat(actualBenefitTypeDTO)
                .usingRecursiveComparison()
                .ignoringFields("typeId")
                .isEqualTo(benefitTypeDTO2);
    }

}
