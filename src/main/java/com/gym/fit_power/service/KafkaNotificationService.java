package com.gym.fit_power.service;

import com.gym.fit_power.dto.KafkaNotificationDTO;

public interface KafkaNotificationService{
    void publishNutritionNotification(KafkaNotificationDTO request);
    void publishTrainingNotification(KafkaNotificationDTO request);
}
