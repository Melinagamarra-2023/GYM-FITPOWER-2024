package com.gym.fit_power.service.impl;

import com.gym.fit_power.dto.request.TrainingDiaryRequestDto;
import com.gym.fit_power.dto.response.TrainingDiaryResponseDto;
import com.gym.fit_power.exception.EntityNotFoundException;
import com.gym.fit_power.model.Client;
import com.gym.fit_power.model.Routine;
import com.gym.fit_power.model.TrainingDiary;
import com.gym.fit_power.repository.ClientRepository;
import com.gym.fit_power.repository.RoutineRepository;
import com.gym.fit_power.repository.TrainingDiaryRepository;
import com.gym.fit_power.service.RoutineService;
import com.gym.fit_power.service.TrainingDiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingDiaryServiceImpl implements TrainingDiaryService {

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    private final TrainingDiaryRepository trainingDiaryRepository;
    private final RoutineRepository routineRepository;
    private final ClientRepository clientRepository;

    @Override
    public TrainingDiaryResponseDto add(TrainingDiaryRequestDto trainingDiaryRequestDto, String clientCuit) {
        var client = clientRepository.findByCuit(clientCuit);
        if (client == null) {
            throw new EntityNotFoundException("Cliente con CUIT: " + clientCuit + " no encontrado");
        }
        Optional<Routine> routine = routineRepository.findByClientAndActiveTrue(client);
        if (routine.isEmpty()) {
            throw new EntityNotFoundException("Rutina no encontrada para el cliente con CUIT: " + clientCuit);
        }
        TrainingDiary trainingDiary = this.toEntity(trainingDiaryRequestDto);
        trainingDiary.setRoutine(routine.get()); // Asigna el ID de la rutina al objeto TrainingDiary
        return this.toDto(trainingDiaryRepository.save(trainingDiary));
    }

    @Override
    public List<TrainingDiaryResponseDto> findByRoutineId(Long routineId) {
        return trainingDiaryRepository.findByRoutineId(routineId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<TrainingDiaryResponseDto> readByClientActiveRoutine(String clientCuit) {
        Client client = clientRepository.findByCuit(clientCuit);
        if (client == null) {
            log.warn("Cliente con CUIT: {} no encontrado al buscar diarios de rutina activa.", clientCuit);
            throw new EntityNotFoundException("Cliente con CUIT: " + clientCuit + " no encontrado.");
            // Alternativamente, si prefieres, podrías registrar un warning y devolver una lista vacía:
            // return Collections.emptyList();
        }

        Optional<Routine> activeRoutineOpt = routineRepository.findByClientAndActiveTrue(client);

        if (activeRoutineOpt.isEmpty()) {
            log.info("No se encontró rutina activa para el cliente con CUIT: {}. Devolviendo lista vacía de diarios.", clientCuit);
            return Collections.emptyList();
        }

        Routine activeRoutine = activeRoutineOpt.get();
        return trainingDiaryRepository.findByRoutineId(activeRoutine.getId())
                .stream()
                .map(this::toDto)
                .toList();
    }

    private TrainingDiary toEntity(TrainingDiaryRequestDto dto) {
        if (dto == null) {
            throw new NullPointerException("dto cannot be null");
        }
        TrainingDiary trainingDiary = new TrainingDiary();
        trainingDiary.setCommentary(dto.getCommentary());
        return trainingDiary;
    }

    public TrainingDiaryResponseDto toDto(TrainingDiary trainingDiary) {
        return TrainingDiaryResponseDto.builder()
                .id(trainingDiary.getId())
                .commentary(trainingDiary.getCommentary())
                .createdAt(trainingDiary.getCreatedAt().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .routineId(trainingDiary.getRoutine().getId())
                .build();
    }

    private String formatCreatedAt(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

}
