package com.example.benefit.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "benefit_type")
public class BenefitType {
//    Benefits can be Accrual or Deduction,
//    so it will increase employees salaries or reduce it.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;

}
