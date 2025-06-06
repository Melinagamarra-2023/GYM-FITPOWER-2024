package com.gym.fit_power.model;

import lombok.*;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nutritionists")
public class Nutritionist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private String lastname;

    @Column(unique = true)
    private String cuit;
    private String email;
    private String phone;
    private LocalDate createdAt;
    private Boolean enabled;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDate.now();
        enabled = true;
    }

}
