package com.gym.fit_power.model;

import lombok.*;

import java.util.List;
import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.gym.fit_power.model.Trainer;
import com.gym.fit_power.model.Nutritionist;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cuit;

    @ManyToOne
//    @JoinColumn(nullable = false)
    private Gym assignedGym;

    @ManyToOne
    private Trainer assignedTrainer;

    @ManyToOne
    private Nutritionist assignedNutritionist;

    @OneToMany(mappedBy = "client")
    private List<Routine> routines;

    @OneToMany(mappedBy = "client")
    private List<NutritionPlan> plans;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String lastname;

    @NotNull
    @NotBlank
    private String email;

    @Column(name = "phone_number")
    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String phone;

    private LocalDate birthDate;

    private boolean enabled;

}
