package com.gym.fit_power.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaNotificationDTO {
    private String date;
    private String sender;
    private String client;
    private String reason;
    private String message;
}
