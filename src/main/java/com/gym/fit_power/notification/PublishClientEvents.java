package com.gym.fit_power.notification;

import java.util.UUID;
import java.time.LocalDateTime;

import com.gym.fit_power.model.Client;
import com.gym.fit_power.notification.event.*;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;

@Component
public class PublishClientEvents {

    private final KafkaTemplate<String, Event<?>> producer;

    public PublishClientEvents(KafkaTemplate<String, Event<?>> producer) {
        this.producer = producer;
    }

    public void publish(Client client) {
        ClientCreatedEvent created = new ClientCreatedEvent();
        created.setId(UUID.randomUUID().toString());
        created.setDate(LocalDateTime.now());
        created.setType(EventType.CREATED);
        created.setEntity(client);
        this.producer.send("clients", created);
    }

}