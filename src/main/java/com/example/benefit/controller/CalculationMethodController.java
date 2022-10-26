package com.example.benefit.controller;

import com.example.benefit.model.CalculationMethodDTO;
import com.example.benefit.service.CalculationMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calculationMethod")
@RequiredArgsConstructor
public class CalculationMethodController {
    private final CalculationMethodService calculationMethodService;

    @PostMapping
    public @ResponseBody CalculationMethodDTO addCalculationMethod(@RequestBody CalculationMethodDTO calculationMethodDTO) {
        return calculationMethodService.createAndUpdateCalculationMethod(calculationMethodDTO);
    }

    @PutMapping
    public CalculationMethodDTO updateCalculationMethod(@RequestBody CalculationMethodDTO calculationMethodDTO) {
        return calculationMethodService.createAndUpdateCalculationMethod(calculationMethodDTO);
    }

    @GetMapping
    public List<CalculationMethodDTO> getAllCalculationMethods() {
        return calculationMethodService.getAllCalculationMethods();
    }

    @GetMapping("/{id}")
    public CalculationMethodDTO findById(@PathVariable Long id) {
        return calculationMethodService.getCalculationMethodById(id);
    }

    @DeleteMapping
    public void deleteCalculationMethod(@RequestParam(value = "id") Long id) {
        calculationMethodService.deleteCalculationMethod(id);
    }
}
