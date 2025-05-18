package com.gym.fit_power.repository;

import com.gym.fit_power.model.KafkaNutritionNotification;
import com.gym.fit_power.model.KafkaTrainingNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface KafkaTrainingRepository extends JpaRepository<KafkaTrainingNotification, Long> {
}
