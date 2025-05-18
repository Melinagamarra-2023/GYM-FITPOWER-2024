package com.gym.fit_power.service;

import com.gym.fit_power.dto.KafkaNotificationDTO;

public interface KafkaNotificationService{
    KafkaNotificationDTO publishNutritionNotification(KafkaNotificationDTO request);
    KafkaNotificationDTO publishTrainingNotification(KafkaNotificationDTO request);
}
