package com.gym.fit_power.service.impl;

import com.gym.fit_power.dto.request.ExerciseSetRequestDto;
import com.gym.fit_power.dto.request.RoutineRequestDto;
import com.gym.fit_power.dto.response.RoutineResponseDto;
import com.gym.fit_power.exception.EntityNotFoundException;
import com.gym.fit_power.model.*;
import com.gym.fit_power.repository.*;
import com.gym.fit_power.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {

    private final RoutineRepository routineRepository;
    private final ClientRepository clientRepository;
    private final TrainerRepository trainerRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseSetServiceImpl exerciseSetServiceImpl;
    private final TrainingDiaryServiceImpl trainingDiaryServiceImpl;

    @Override
    public RoutineResponseDto save(RoutineRequestDto routineRequestDto, String trainerCuit) {
        Optional<Trainer> trainerOpt = trainerRepository.findByCuit(trainerCuit);
        Trainer trainer = trainerOpt.orElseThrow(() ->
                new EntityNotFoundException("Trainer with CUIT " + trainerCuit + " not found."));
        String clientCuit = routineRequestDto.getClientCuit();
        Client client = clientRepository.findByCuit(clientCuit);
        if (client == null) {
            throw new EntityNotFoundException("Client with CUIT " + clientCuit + " not found.");
        }
        Routine newRoutine = this.toEntity(routineRequestDto);
        newRoutine.setTrainer(trainer);
        newRoutine.setClient(client);

        List<ExerciseSetRequestDto> exerciseSetRequestDtos = routineRequestDto.getExerciseSets();
        List<ExerciseSet> exerciseSets = new ArrayList<>();
        if (exerciseSetRequestDtos != null) {
            for (ExerciseSetRequestDto exerciseSetRequestDto : exerciseSetRequestDtos) {
                Exercise exercise = exerciseRepository.findById(exerciseSetRequestDto.getExerciseId())
                        .orElseThrow(() -> new EntityNotFoundException("Exercise with ID " + exerciseSetRequestDto.getExerciseId() + " not found")); // Usa EntityNotFoundException
                ExerciseSet exerciseSet = exerciseSetServiceImpl.toEntity(exerciseSetRequestDto, newRoutine, exercise);
                exerciseSets.add(exerciseSet);
            }
        }

        newRoutine.setExerciseSets(exerciseSets);

        RoutineResponseDto activeRoutineDto = findClientActiveRoutine(clientCuit);
        if (activeRoutineDto != null) {
            disableActiveRoutine(activeRoutineDto.getId());
        }

        Routine savedRoutine = routineRepository.save(newRoutine);
        return this.toDto(savedRoutine);
    }

    @Override
    public List<RoutineResponseDto> findByClient(String clientCuit) {
        Optional<Client> client = clientRepository.findAll()
                .stream()
                .filter(c -> c.getCuit().equals(clientCuit))
                .findFirst();
        if (client.isEmpty()) {
            throw new EntityNotFoundException("Client with CUIT " + clientCuit + " not found.");
        }
        List<Routine> routines = routineRepository.findByClient(client.get());
        return routines.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public RoutineResponseDto findClientActiveRoutine(String clientCuit) {
        Client client = clientRepository.findByCuit(clientCuit);
        if (client == null) {
            throw new EntityNotFoundException("Client with CUIT " + clientCuit + " not found.");
        }
        Optional<Routine> routineOpt = routineRepository.findByClientAndActiveTrue(client);
        return routineOpt.map(this::toDto).orElse(null); // Devuelve el DTO si existe, sino null.
    }

    @Override
    public void disableActiveRoutine(Long routineId) {
        routineRepository.findById(routineId)
                .ifPresent(r -> {
                    r.setActive(false);
                    routineRepository.save(r);
                });
    }

    private RoutineResponseDto toDto(Routine routine) {
        return RoutineResponseDto.builder()
                .id(routine.getId())
                .goals(routine.getGoals())
                .createdAt(routine.getCreatedAt().toString())

                .trainerCuit(routine.getTrainer().getCuit())
                .clientCuit(routine.getClient().getCuit())
                .exerciseSets(routine.getExerciseSets()
                        .stream()
                        .map(exerciseSetServiceImpl::toDto)
                        .toList()
                )
                .trainingDiaries((routine.getTrainingDiaries() != null) ? routine.getTrainingDiaries()
                        .stream()
                        .map(trainingDiaryServiceImpl::toDto)
                        .collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }

    private Routine toEntity(RoutineRequestDto dto) {
        return Routine.builder()
                .goals(dto.getGoals())
                .build();
    }

}
