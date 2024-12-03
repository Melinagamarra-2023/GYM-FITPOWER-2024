package com.gym.fit_power.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingDiaryResponseDto {
    private Long id;
    private String commentary;
    private String createdAt;
    private Long routineId;
}
