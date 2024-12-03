package com.gym.fit_power.service;

import com.gym.fit_power.dto.request.RoutineRequestDto;
import com.gym.fit_power.dto.response.RoutineResponseDto;

import java.util.List;

public interface RoutineService {
    RoutineResponseDto save(RoutineRequestDto routineRequestDto, String clientCuit);
    List<RoutineResponseDto> findByClient(String clientCuit);
    RoutineResponseDto findClientActiveRoutine(String clientCuit);
    void disableActiveRoutine(Long routineId);
}
