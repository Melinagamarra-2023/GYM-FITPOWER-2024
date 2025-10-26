package com.gym.fit_power.service;

import com.gym.fit_power.dto.request.CreateNutriRequest;
import com.gym.fit_power.dto.response.ResponseNutri;

import java.util.List;

public interface NutritionistService {

    ResponseNutri create (CreateNutriRequest requestNutri);
    ResponseNutri readOne (Long id);
    List<ResponseNutri> readAll ();
    ResponseNutri update (Long id , CreateNutriRequest requestNutri);
    ResponseNutri disable (Long id);
    ResponseNutri enable (Long id );
    ResponseNutri findByCuit ( String cuit );


}
