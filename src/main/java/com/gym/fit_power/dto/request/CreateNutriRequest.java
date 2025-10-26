package com.gym.fit_power.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateNutriRequest {

    @JsonIgnore
    private Long id;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastname;
    @Pattern(regexp = "\\d{11}", message = "El CUIT debe tener 11 dígitos numéricos")
    @NotBlank(message = "El cuit no puede estar vacío")
    private String cuit;
    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;
    @NotBlank(message = "El num. celular no puede estar vacío")
    private String phone;
}
