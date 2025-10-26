package com.gym.fit_power.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionDiaryDTO {
    @JsonIgnore
    private Long id;
    private String updateAt;
    @NotBlank
    private String breakfast;
    @NotBlank
    private String lunch;
    @NotBlank
    private String snacks;
    @NotBlank
    private String dinner;
    @NotBlank
    private Float actualWeight;
    @NotBlank
    private String comentary;



}
