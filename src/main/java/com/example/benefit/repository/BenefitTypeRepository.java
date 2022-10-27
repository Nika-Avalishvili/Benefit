package com.example.benefit.repository;

import com.example.benefit.model.BenefitType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BenefitTypeRepository extends JpaRepository<BenefitType, Long> {
    BenefitType findByName(String name);
}
