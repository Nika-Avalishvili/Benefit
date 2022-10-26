package com.example.benefit.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculationMethod {
//    Calculation method defines how the specific amount
//    shall be taken into consideration when relevant person
//    calculates employee salaries. Amount can be Net or Gross.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
