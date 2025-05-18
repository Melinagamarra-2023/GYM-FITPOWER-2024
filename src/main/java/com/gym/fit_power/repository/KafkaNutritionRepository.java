package com.gym.fit_power.repository;

import com.gym.fit_power.model.KafkaNutritionNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface KafkaNutritionRepository extends JpaRepository<KafkaNutritionNotification, Long> {
}
