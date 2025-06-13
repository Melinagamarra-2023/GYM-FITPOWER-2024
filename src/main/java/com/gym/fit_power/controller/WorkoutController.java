package com.gym.fit_power.controller;

import com.gym.fit_power.dto.request.RoutineRequestDto;
import com.gym.fit_power.dto.request.TrainingDiaryRequestDto;
import com.gym.fit_power.dto.response.RoutineResponseDto;
import com.gym.fit_power.dto.response.TrainingDiaryResponseDto;
import com.gym.fit_power.service.RoutineService;
import com.gym.fit_power.service.TrainingDiaryService;
import com.gym.fit_power.util.DecodeUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/workout")
public class WorkoutController {

    private final RoutineService routineService;
    private final TrainingDiaryService trainingDiaryService;

    // El entrenador revisa las rutinas de un cliente
    @GetMapping("/routines/{clientCuit}")
    @PreAuthorize("hasAnyRole('TRAINER')")
    public ResponseEntity<List<RoutineResponseDto>> viewAClientRoutines(@PathVariable String clientCuit) {
        return new ResponseEntity<>(routineService.findByClient(clientCuit), HttpStatus.OK);
    }

    // El entrenador revisa la rutina activa de un cliente
    @GetMapping("/routine/{clientCuit}")
    @PreAuthorize("hasAnyRole('TRAINER')")
    public ResponseEntity<RoutineResponseDto> viewAClientActiveRoutine(@PathVariable String clientCuit) {
        return new ResponseEntity<>(routineService.findClientActiveRoutine(clientCuit), HttpStatus.OK);
    }

    // El entrenador crea una rutina para un cliente
    @PostMapping("/routine")
    @PreAuthorize("hasAnyRole('TRAINER')")
    public ResponseEntity<RoutineResponseDto> createARoutineForAClient(@Valid @RequestBody RoutineRequestDto request,
                                                                       @RequestHeader String authorization) {
        String trainerCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(routineService.save(request, trainerCuit), HttpStatus.CREATED);
    }

    // <<<<<<<<<<<<<<<< TRAINING-DIARY >>>>>>>>>>>>>>>> //

    // El cliente agrega un diario de entrenamiento
    @PostMapping("/routine/diary")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<TrainingDiaryResponseDto> addTrainingDiaryForAClient(@Valid @RequestBody TrainingDiaryRequestDto request,
                                                                               @RequestHeader String authorization) {
        String clientCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(trainingDiaryService.add(request, clientCuit),HttpStatus.CREATED);
    }

    // El cliente revisa un diario de entrenamiento
    @GetMapping("/routine/diary/{routineId}")
    public ResponseEntity<List<TrainingDiaryResponseDto>> viewTrainingDiaryOfAClientRoutine(@PathVariable Long routineId){
        return new ResponseEntity<>(trainingDiaryService.findByRoutineId(routineId), HttpStatus.OK);
    }

    // El cliente revisa el diario de entrenamiento de la rutina activa
    @GetMapping("/routine/diary/active")
    public ResponseEntity<List<TrainingDiaryResponseDto>> viewActiveTrainingDiary(@RequestHeader String authorization) {
        String clientCuit = DecodeUtil.extractCuitFromToken(authorization);
        return new ResponseEntity<>(trainingDiaryService.readByClientActiveRoutine(clientCuit), HttpStatus.OK);
    }

}
