package com.example.benefit.repository;

import com.example.benefit.model.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    Benefit findByName(String name);

}
