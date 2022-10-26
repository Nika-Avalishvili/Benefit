package com.example.benefit.controller;

import com.example.benefit.model.CalculationMethodDTO;
import com.example.benefit.service.CalculationMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalculationMethodController {
    private final CalculationMethodService calculationMethodService;

    @PostMapping("/calculationMethod")
    public @ResponseBody CalculationMethodDTO addCalculationMethod(@RequestBody CalculationMethodDTO calculationMethodDTO) {
        return calculationMethodService.createAndUpdateCalculationMethod(calculationMethodDTO);
    }

    @PutMapping("/calculationMethod")
    public CalculationMethodDTO updateCalculationMethod(@RequestBody CalculationMethodDTO calculationMethodDTO) {
        return calculationMethodService.createAndUpdateCalculationMethod(calculationMethodDTO);
    }

    @GetMapping("/calculationMethod")
    public List<CalculationMethodDTO> getAllCalculationMethods() {
        return calculationMethodService.getAllCalculationMethods();
    }

    @GetMapping("/calculationMethod/{id}")
    public CalculationMethodDTO findById(@PathVariable Long id) {
        return calculationMethodService.getCalculationMethodById(id);
    }

    @DeleteMapping("/calculationMethod")
    public void deleteCalculationMethod(@RequestParam(value = "id") Long id) {
        calculationMethodService.deleteCalculationMethod(id);
    }
}
