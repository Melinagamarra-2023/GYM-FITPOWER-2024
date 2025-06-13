package com.gym.fit_power.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @Column(name = "phone_number")
    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String phone;
    private LocalDate createdAt;
    private boolean enabled;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDate.now();
        enabled = true;
    }

}
