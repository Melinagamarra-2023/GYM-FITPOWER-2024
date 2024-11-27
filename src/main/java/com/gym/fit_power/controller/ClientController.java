package com.gym.fit_power.controller;

import java.net.URI;
import java.util.List;

import com.gym.fit_power.dto.NutriPlanDTO;
import com.gym.fit_power.dto.response.RoutineResponseDto;
import org.slf4j.Logger;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;

import java.net.URISyntaxException;

import com.gym.fit_power.dto.ClientDTO;
import com.gym.fit_power.service.impl.*;
import com.gym.fit_power.dto.NutritionDiaryDTO;
import org.springframework.web.bind.annotation.*;

import static com.gym.fit_power.constant.ClientConstants.*;

@Slf4j
@RestController
@RequestMapping(ClientController.RESOURCE)
public class ClientController {

    private final ClientServiceImpl clientService;
    //private final RoutineServiceImpl routineService;
    private final NutriPlanServiceImpl nutritionPlanService;
    //private final RoutineDiaryServiceImpl routineDiaryService;
    private final NutritionDiaryServiceImpl nutritionDiaryService;

    public static final String CUIT = "/{cuit}";
    public static final String RESOURCE = "/api/clients";
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    public ClientController(ClientServiceImpl clientService,
                            NutriPlanServiceImpl nutritionPlanService, NutritionDiaryServiceImpl nutritionDiaryService/*,
                            RoutineServiceImpl routineService, RoutineDiaryServiceImpl routineDiaryService*/) {
        this.clientService = clientService;
        //this.routineService = routineService;
        //this.routineDiaryService = routineDiaryService;
        this.nutritionPlanService = nutritionPlanService;
        this.nutritionDiaryService = nutritionDiaryService;
    }

    // <<<<<<<<<<<<<<<<<<< CLIENTS >>>>>>>>>>>>>>>>>>> //

    @PostMapping
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO request) throws URISyntaxException {
        newInfoLog("Creating new client: " + request);
        ClientDTO response = clientService.create(request);
        newInfoLog(WITH_CUIT + request.getCuit() + " is created");
        return ResponseEntity.ok().headers(newHeader("CREATED", SUCCESSFUL)).
                location(new URI("/api/clients/" + response.getCuit())).body(response);
    }

    @GetMapping(value = CUIT)
    public ResponseEntity<ClientDTO> readOne(@PathVariable(value = "cuit") String cuit) {
        newInfoLog("Get client with cuit: " + cuit);
        ClientDTO response = clientService.readByCuit(cuit);
        correctSearch();
        return ResponseEntity.ok().headers(newHeader(FOUND, SUCCESSFUL)).body(response);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ClientDTO>> readAll() {
        newInfoLog("Get all client");
        List<ClientDTO> response = new ArrayList<>(clientService.readAll());
        correctSearch();
        return ResponseEntity.ok().headers(newHeader(FOUND, SUCCESSFUL)).body(response);
    }

    @PutMapping(value = CUIT)
    public ResponseEntity<ClientDTO> update(@PathVariable(value = "cuit") String cuit, @RequestBody ClientDTO client) {
        newInfoLog("Update personal data of client with cuit: " + cuit);
        ClientDTO response = clientService.update(clientService.readByCuit(cuit).getId(), client);
        newInfoLog(genericDescription(cuit) + "is updated");
        return ResponseEntity.ok().headers(newHeader("UPDATED", SUCCESSFUL)).body(response);
    }

    @PutMapping(value = CUIT + "/{gymCode}")
    public ResponseEntity<ClientDTO> changeGym(@PathVariable(value = "cuit") String clientCuit,
                                               @PathVariable(value = "gymCode") String gymCode) {
        newInfoLog("Change the gym of the client " + clientCuit);
        ClientDTO response = clientService.changeGym(clientCuit, gymCode);
        newInfoLog("The gym of client " + clientCuit + " has changed successfully");
        return ResponseEntity.ok().headers(newHeader("GYM_CHANGED", SUCCESSFUL)).body(response);
    }

    @DeleteMapping(value = CUIT)
    public ResponseEntity<ClientDTO> disable(@PathVariable(value = "cuit") String cuit) {
        newInfoLog("Disabling client with cuit: " + cuit);
        ClientDTO response = clientService.disable(clientService.readByCuit(cuit).getId());
        newInfoLog("Client disabled");
        return ResponseEntity.ok().headers(newHeader("DISABLED", SUCCESSFUL)).body(response);
    }

    @PatchMapping(value = CUIT)
    public ResponseEntity<ClientDTO> enable(@PathVariable(value = "cuit") String cuit) {
        newInfoLog("Enabling client with cuit: " + cuit);
        ClientDTO response = clientService.enable(clientService.readByCuit(cuit).getId());
        newInfoLog("Client enabled");
        return ResponseEntity.ok().headers(newHeader("ENABLED", SUCCESSFUL)).body(response);
    }
/*
    // <<<<<<<<<<<<<<<<<<< ROUTINES >>>>>>>>>>>>>>>>>>> //

    @GetMapping(CUIT + "/routines")
    public ResponseEntity<List<RoutineResponseDto>> viewRoutines(@PathVariable(value = "cuit") String clientCuit) {
        return new ResponseEntity<>(routineService.readByClient(clientCuit), HttpStatus.OK);
    }

    @GetMapping(CUIT + "/routines/active")
    public ResponseEntity<RoutineResponseDto> viewActiveRoutine(@PathVariable(value = "cuit") String clientCuit) {
        return new ResponseEntity<>(routineService.readRoutineActiveByClient(clientCuit), HttpStatus.OK);
    }

    @GetMapping(CUIT + "/routines/{ID}")
    public ResponseEntity<RoutineResponseDto> viewOneRoutine(@PathVariable(value = "cuit") String clientCuit,
                                                    @PathVariable(value = "ID") Long ID) {
        return new ResponseEntity<>(routineService.readRoutineByClient(clientCuit, ID), HttpStatus.OK);
    }

    // <<<<<<<<<<<<<<<< ROUTINE-DIARY >>>>>>>>>>>>>>>> //

    @PostMapping(CUIT + "/routines/active/diary")
    public ResponseEntity<RoutineDiaryDTO> createRoutineDiary(@PathVariable(value = "cuit") String clientCuit,
                                                                  @Valid @RequestBody RoutineDiaryDTO request) {
        return new ResponseEntity<>(routineDiaryService.create(clientCuit, request), HttpStatus.CREATED);
    }

    @GetMapping(CUIT + "/routines/{ID}/diary")
    public ResponseEntity<List<RoutineDiaryDTO>> viewRoutineDiary(@PathVariable(value = "cuit") String clientCuit,
                                                                      @PathVariable(value = "ID") String ID) {
        return new ResponseEntity<>(routineDiaryService.readByRoutine(ID), HttpStatus.OK);
    }

    @GetMapping(CUIT + "/routines/active/diary")
    public ResponseEntity<List<RoutineDiaryDTO>> viewActiveRoutineDiary(@PathVariable(value = "cuit") String clientCuit) {
        return new ResponseEntity<>(routineDiaryService.readByClientActiveRoutine(clientCuit), HttpStatus.OK);
    }
    */


    @GetMapping(CUIT + "/nutrition_plans")
    public ResponseEntity<List<NutriPlanDTO>> viewNutritionPlans(@PathVariable(value = "cuit") String clientCuit) {
        return new ResponseEntity<>(nutritionPlanService.readByClient(clientCuit), HttpStatus.OK);
    }

    @GetMapping(CUIT + "/nutrition_plans/active")
    public ResponseEntity<NutriPlanDTO> viewActivePlan(@PathVariable(value = "cuit") String clientCuit) {
        return new ResponseEntity<>(nutritionPlanService.readPlanActiveByClient(clientCuit), HttpStatus.OK);
    }

    @GetMapping(CUIT + "/nutrition_plans/{ID}")
    public ResponseEntity<NutriPlanDTO> viewOnePlan(@PathVariable(value = "cuit") String clientCuit,
                                                    @PathVariable(value = "ID") Long ID) {
        return new ResponseEntity<>(nutritionPlanService.readPlanByClient(clientCuit, ID), HttpStatus.OK);
    }

    /*
    <<<<<<<<<<<<<<< NUTRITION-DIARY >>>>>>>>>>>>>>>

    @PostMapping(CUIT + "/nutrition_plans/active/diary")
    public ResponseEntity<NutritionDiaryDTO> createNutritionDiary(@PathVariable(value = "cuit") String clientCuit,
                                                                  @Valid @RequestBody NutritionDiaryDTO request) {
        return new ResponseEntity<>(nutritionDiaryService.create(clientCuit, request), HttpStatus.CREATED);
    }

    @PutMapping(CUIT + "/nutrition_plans/active/diary")
    public ResponseEntity<NutritionDiaryDTO> updateNutritionDiary(@PathVariable(value = "cuit") String clientCuit,
                                                                  @RequestBody NutritionDiaryDTO request) {
        return new ResponseEntity<>(nutritionDiaryService.update(clientCuit, request), HttpStatus.OK);
    }

    @GetMapping(CUIT + "/nutrition_plans/{ID}/diary")
    public ResponseEntity<List<NutritionDiaryDTO>> viewNutriPlanDiary(@PathVariable(value = "cuit") String clientCuit,
                                                                      @PathVariable(value = "ID") String ID) {
        return new ResponseEntity<>(nutritionDiaryService.readByNutritionPlan(ID), HttpStatus.OK);
    }

    @GetMapping(CUIT + "/nutrition_plans/active/diary")
    public ResponseEntity<List<NutritionDiaryDTO>> viewActivePlanDiary(@PathVariable(value = "cuit") String clientCuit) {
        return new ResponseEntity<>(nutritionDiaryService.readByClientActivePlan(clientCuit), HttpStatus.OK);
    }
*/
    // <<<<<<<<<<<<<<<<<<<< UTILS >>>>>>>>>>>>>>>>>>>> //

    private void newInfoLog(String description) {
        logger.info(CONTROLLER, description);
    }

    private void correctSearch() {
        logger.info(SEARCH_CORRECT);
    }

    private HttpHeaders newHeader(String headerName, String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, description);
        return headers;
    }

    private String genericDescription(String cuit) {
        return WITH_CUIT + cuit + " ";
    }

}
