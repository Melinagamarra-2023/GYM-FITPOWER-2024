package com.gym.fit_power.service.impl;

import java.time.LocalDateTime;
import com.gym.fit_power.dto.KafkaNotificationDTO;
import com.gym.fit_power.model.*;
import com.gym.fit_power.repository.*;
import com.gym.fit_power.service.KafkaNotificationService;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;

@Component
public class KafkaNotificationServiceImpl implements KafkaNotificationService {

    private final KafkaTemplate<String, KafkaNotification<?>> producer;
    private final KafkaNutritionRepository kafkaNutritionRepository;
    private final KafkaTrainingRepository kafkaTrainingRepository;
    private final ClientRepository clientRepository;
    private final NutriRepository nutriRepository;
    private final TrainerRepository trainerRepository;

    public KafkaNotificationServiceImpl(KafkaTemplate<String,
            KafkaNotification<?>> producer, KafkaNutritionRepository kafkaNutritionRepository,
                                        KafkaTrainingRepository kafkaTrainingRepository, ClientRepository clientRepository,
                                        NutriRepository nutriRepository, TrainerRepository trainerRepository) {
        this.producer = producer;
        this.kafkaNutritionRepository = kafkaNutritionRepository;
        this.kafkaTrainingRepository = kafkaTrainingRepository;
        this.clientRepository = clientRepository;
        this.nutriRepository = nutriRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public void publishNutritionNotification(KafkaNotificationDTO dto) {
        KafkaNutritionNotification notification = new KafkaNutritionNotification();
        notification.setDate(LocalDateTime.now());
        notification.setSender(nutriRepository.findByCuit(dto.getSender()));
        notification.setClient(clientRepository.findByCuit(dto.getClient()));
        notification.setReason(dto.getReason());
        notification.setMessage(dto.getMessage());
        this.producer.send("nutrition", notification);
        kafkaNutritionRepository.save(notification);
    }

    @Override
    public void publishTrainingNotification(KafkaNotificationDTO dto) {
        KafkaTrainingNotification notification = new KafkaTrainingNotification();
        notification.setDate(LocalDateTime.now());
        Gym gym = new Gym(5L, "addres", "domain", "mail", "phone", true);
        notification.setSender(trainerRepository.findByCuit(dto.getSender()));
        notification.setClient(clientRepository.findByCuit(dto.getClient()));
        notification.setReason(dto.getReason());
        notification.setMessage(dto.getMessage());
        this.producer.send("train", notification);
        kafkaTrainingRepository.save(notification);
    }

    public KafkaNotificationDTO toDTO(KafkaNotification<?> entity) {
        KafkaNotificationDTO dto = new KafkaNotificationDTO();
        dto.setDate(entity.getDate().toString());
        dto.setSender(entity.getSender().toString());
        dto.setClient(entity.getClient().toString());
        dto.setReason(entity.getReason());
        dto.setMessage(entity.getMessage());
        return dto;
    }
}