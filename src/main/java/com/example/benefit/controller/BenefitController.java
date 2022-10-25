package com.example.benefit.controller;

import com.example.benefit.model.BenefitDTO;
import com.example.benefit.service.BenefitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BenefitController {

    private final BenefitService benefitService;

    @PostMapping("/benefit")
    public @ResponseBody BenefitDTO addBenefit(@RequestBody BenefitDTO benefitDTO){
        return benefitService.createAndUpdateBenefit(benefitDTO);
    }

    @PutMapping("/benefit")
    public BenefitDTO updateBenefit(@RequestBody BenefitDTO benefitDTO){
        return benefitService.createAndUpdateBenefit(benefitDTO);
    }

    @GetMapping("/benefit")
    public List<BenefitDTO> getAllBenefits(){
        return benefitService.getAllBenefit();
    }

    @GetMapping("/benefit/{id}")
    public BenefitDTO findById(@PathVariable Long id) {
        return benefitService.getBenefitById(id);
    }

    @DeleteMapping("/benefit")
    public void deleteBenefit(@RequestParam (value = "id") Long id) {
        benefitService.deleteBenefit(id);
    }


}
