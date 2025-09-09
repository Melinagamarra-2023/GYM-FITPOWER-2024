package com.gym.fit_power.service.impl;

import com.gym.fit_power.dto.event.TrainerCreatedEventDTO;
import com.gym.fit_power.dto.event.TrainerUpdatedEventDTO;
import com.gym.fit_power.dto.request.TrainerRequestDto;
import com.gym.fit_power.dto.response.TrainerResponseDto;
import com.gym.fit_power.exception.EntityNotFoundException;
import com.gym.fit_power.exception.EntitySaveException;
import com.gym.fit_power.model.Trainer;
import com.gym.fit_power.repository.TrainerRepository;
import com.gym.fit_power.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private static final String TRAINER_EVENTS_TOPIC = "trainer-events";
    private final TrainerRepository trainerRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<TrainerResponseDto> findAll() {
        return trainerRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerResponseDto findByCuit(String cuit) throws EntityNotFoundException {
        Optional<Trainer> trainer = trainerRepository.findByCuit(cuit);
        return trainer.map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Trainer with CUIT " + cuit + " not found."));
    }

    @Override
    @Transactional
    public TrainerResponseDto save(TrainerRequestDto trainerRequestDto) throws EntitySaveException {
        trainerRepository.findByCuit(trainerRequestDto.getCuit())
                .ifPresent(trainer -> {
                    throw new EntitySaveException("Trainer with CUIT " + trainerRequestDto.getCuit() + " already exists.");
                });
        Trainer newTrainer = this.toEntity(trainerRequestDto);
        newTrainer = trainerRepository.save(newTrainer);

        TrainerCreatedEventDTO event = TrainerCreatedEventDTO.builder()
                .name(newTrainer.getName())
                .lastname(newTrainer.getLastname())
                .cuit(newTrainer.getCuit())
                .email(newTrainer.getEmail())
                .build();

        sendTrainerEvent(event);

        return this.toDto(newTrainer);
    }

    @Override
    @Transactional
    public TrainerResponseDto update(String cuit, TrainerRequestDto trainerRequestDto) throws EntitySaveException {
        Trainer trainer = trainerRepository.findByCuit(cuit)
                .orElseThrow(() -> new EntitySaveException("No trainer found with CUIT " + cuit + " for update."));

        Trainer updatedTrainer = this.toEntity(trainerRequestDto);
        updatedTrainer.setCuit(cuit);
        updatedTrainer.setId(trainer.getId());

        trainerRepository.save(updatedTrainer);

        TrainerUpdatedEventDTO event = TrainerUpdatedEventDTO.builder()
                .name(updatedTrainer.getName())
                .lastname(updatedTrainer.getLastname())
                .cuit(updatedTrainer.getCuit())
                .email(updatedTrainer.getEmail())
                .build();

        sendTrainerEvent(event);

        return this.toDto(updatedTrainer);
    }

    @Override
    @Transactional
    public void delete(String cuit) throws EntityNotFoundException {
        Trainer trainer = trainerRepository.findByCuit(cuit)
                .orElseThrow(() -> new EntityNotFoundException("No trainer found with DNI " + cuit + " for deletion."));
        trainer.setEnabled(false);
        trainerRepository.save(trainer);
    }

    private void sendTrainerEvent(Object event) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TRAINER_EVENTS_TOPIC, event);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Evento de trainer enviado con éxito al topic '{}', partición {}, con offset {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("No se pudo enviar el evento de trainer a Kafka. Causa: {}", ex.getMessage());
            }
        });
    }

    private Trainer toEntity(TrainerRequestDto dto) {
        Trainer trainer = new Trainer();
        trainer.setCuit(dto.getCuit());
        trainer.setName(dto.getName());
        trainer.setLastname(dto.getLastname());
        trainer.setEmail(dto.getEmail());
        trainer.setPhoneNumber(dto.getPhoneNumber());
        return trainer;
    }

    private TrainerResponseDto toDto(Trainer trainer) {
        return TrainerResponseDto.builder()
                .id(trainer.getId())
                .cuit(trainer.getCuit())
                .name(trainer.getName())
                .lastname(trainer.getLastname())
                .email(trainer.getEmail())
                .phoneNumber(trainer.getPhoneNumber())
                .createdAt(trainer.getCreatedAt().toString())
                .enabled(trainer.isEnabled())
                .build();
    }

}
