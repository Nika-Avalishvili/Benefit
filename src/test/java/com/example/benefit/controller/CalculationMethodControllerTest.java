package com.example.benefit.controller;

import com.example.benefit.model.CalculationMethodDTO;
import com.example.benefit.repository.CalculationMethodRepository;
import com.example.benefit.service.CalculationMethodService;
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
public class CalculationMethodControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CalculationMethodService calculationMethodService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CalculationMethodRepository calculationMethodRepository;

    @BeforeEach
    void cleanUp() {
        calculationMethodRepository.deleteAll();
    }

    @Test
    void getAllCalculationMethods() throws Exception {
        CalculationMethodDTO calculationMethodDTO = CalculationMethodDTO.builder().name("Salary").build();

        calculationMethodService.createAndUpdateCalculationMethod(calculationMethodDTO);
        List<CalculationMethodDTO> expectedCalculationMethodDTOList = List.of(calculationMethodDTO);

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.get("/calculationMethod"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<CalculationMethodDTO> actualCalculationMethodDTOList = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        Assertions.assertEquals(1, expectedCalculationMethodDTOList.size());
        Assertions.assertEquals(1, actualCalculationMethodDTOList.size());

        CalculationMethodDTO expectedCalculationMethodDto = expectedCalculationMethodDTOList.stream().findFirst().orElseThrow();
        CalculationMethodDTO actualCalculationMethodDto = actualCalculationMethodDTOList.stream().findFirst().orElseThrow();

        assertThat(expectedCalculationMethodDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(actualCalculationMethodDto);
    }


    @Test
    void getCalculationMethodById() throws Exception {
        CalculationMethodDTO calculationMethodDTO = CalculationMethodDTO.builder().name("Salary").build();

        Long id = calculationMethodService.createAndUpdateCalculationMethod(calculationMethodDTO).getId();

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.get("/calculationMethod/{id}", id))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        CalculationMethodDTO actualCalculationMethodDTO = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        assertThat(actualCalculationMethodDTO)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(calculationMethodDTO);
    }

    @Test
    void addCalculationMethod() throws Exception {
        CalculationMethodDTO calculationMethodDTO = CalculationMethodDTO.builder()
                .name("Salary")
                .build();

        String requestJson = objectMapper.writeValueAsString(calculationMethodDTO);

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.post("/calculationMethod")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        CalculationMethodDTO actualCalculationMethodDTO = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        assertThat(actualCalculationMethodDTO)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(calculationMethodDTO);
    }

    @Test
    void updateCalculationMethod() throws Exception {
        CalculationMethodDTO calculationMethodDTO = CalculationMethodDTO.builder()
                .name("Salary")
                .build();

        calculationMethodService.createAndUpdateCalculationMethod(calculationMethodDTO);

        String requestJson = objectMapper.writeValueAsString(calculationMethodDTO);

        String responseAsAString = mockMvc.perform(MockMvcRequestBuilders.put("/calculationMethod")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        CalculationMethodDTO actualCalculationMethodDTO = objectMapper.readValue(responseAsAString, new TypeReference<>() {
        });

        assertThat(actualCalculationMethodDTO)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(calculationMethodDTO);
    }

    @Test
    void deleteCalculationMethod() throws Exception {
        CalculationMethodDTO calculationMethodDTO1 = CalculationMethodDTO.builder()
                .name("Meal allowance")
                .build();
        CalculationMethodDTO calculationMethodDTO2 = CalculationMethodDTO.builder()
                .name("Car allowance")
                .build();

        Long firstId = calculationMethodService.createAndUpdateCalculationMethod(calculationMethodDTO1).getId();
        calculationMethodService.createAndUpdateCalculationMethod(calculationMethodDTO2);

        String firstResponseAsAString = mockMvc.perform(MockMvcRequestBuilders.delete("/calculationMethod?id={id}", firstId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String lastResponseAsAString = mockMvc.perform(MockMvcRequestBuilders.get("/calculationMethod"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<CalculationMethodDTO> actualCalculationMethodDTOList = objectMapper.readValue(lastResponseAsAString, new TypeReference<>() {
        });

        assertThat(actualCalculationMethodDTOList)
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .doesNotContain(calculationMethodDTO1);

        CalculationMethodDTO actualCalculationMethodDTO = actualCalculationMethodDTOList.stream().findFirst().orElseThrow();

        assertThat(actualCalculationMethodDTO)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(calculationMethodDTO2);
    }

}
