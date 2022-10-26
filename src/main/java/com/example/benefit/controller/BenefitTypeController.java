package com.example.benefit.controller;

import com.example.benefit.model.BenefitTypeDTO;
import com.example.benefit.service.BenefitTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/benefitType")
@RequiredArgsConstructor
public class BenefitTypeController {
    private final BenefitTypeService benefitTypeService;

    @PostMapping
    public @ResponseBody BenefitTypeDTO addBenefitType(@RequestBody BenefitTypeDTO benefitTypeDTO) {
        return benefitTypeService.createAndUpdateBenefitType(benefitTypeDTO);
    }

    @PutMapping
    public BenefitTypeDTO updateBenefitType(@RequestBody BenefitTypeDTO benefitTypeDTO) {
        return benefitTypeService.createAndUpdateBenefitType(benefitTypeDTO);
    }

    @GetMapping
    public List<BenefitTypeDTO> getAllBenefitTypes() {
        return benefitTypeService.getAllBenefitType();
    }

    @GetMapping("/{id}")
    public BenefitTypeDTO findById(@PathVariable Long id) {
        return benefitTypeService.getBenefitTypeById(id);
    }

    @DeleteMapping
    public void deleteBenefitType(@RequestParam(value = "id") Long id) {
        benefitTypeService.deleteBenefitType(id);
    }

}
