package com.gym.fit_power.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public abstract class KafkaNotification<E> {

    private LocalDateTime date;
    private E sender;
    private Client client;
    private String reason;
    private String message;
}