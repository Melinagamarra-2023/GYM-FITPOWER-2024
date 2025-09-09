package com.gym.fit_power.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerCreatedEventDTO {
    private String name;
    private String lastname;
    private String cuit;
    private String email;
}
