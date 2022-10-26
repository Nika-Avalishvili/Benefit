package com.example.benefit.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "calculation_method")
public class CalculationMethod {
//    Calculation method defines how the specific amount
//    shall be taken into consideration when relevant person
//    calculates employee salaries. Amount can be Net or Gross.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;

}
