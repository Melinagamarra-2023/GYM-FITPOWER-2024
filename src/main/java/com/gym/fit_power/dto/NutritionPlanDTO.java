package com.gym.fit_power.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionPlanDTO {
    @JsonIgnore
    private Long id;
    @NotBlank
    private Float dailyCalories;
    @NotBlank
    private Float dailyCarbohydrates;
    @NotBlank
    private Float dailyProteins;
    @NotBlank
    private Float dailyFats;
    @NotBlank
    private Float desiredWeight;
    @Pattern(regexp = "\\d{11}", message = "El CUIT debe tener 11 dígitos numéricos")
    @NotBlank
    private String nutritionistCuit;
    @Pattern(regexp = "\\d{11}", message = "El CUIT debe tener 11 dígitos numéricos")
    @NotBlank
    private String ClientCuit;


}
