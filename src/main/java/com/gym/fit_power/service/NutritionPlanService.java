package com.gym.fit_power.service;

import com.gym.fit_power.dto.NutritionPlanDTO;

import java.util.List;


public interface NutritionPlanService {

    NutritionPlanDTO create(NutritionPlanDTO request);

    NutritionPlanDTO readOne(Long id);

    List<NutritionPlanDTO> readByClient(String clientCuit);

    NutritionPlanDTO readPlanActiveByClient(String clientCuit);

    NutritionPlanDTO readPlanByClient(String clientCuit, Long id);


}
