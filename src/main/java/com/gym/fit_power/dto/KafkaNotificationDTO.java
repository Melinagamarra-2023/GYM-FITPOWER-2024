package com.gym.fit_power.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KafkaNotificationDTO {
    private String date;
    private String sender;
    private String client;
    private String reason;
    private String message;
}
