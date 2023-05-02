package com.messenger.publisher.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "direct", groupId = "private")
    public void listenMessage(String message) {
        System.out.println("Received message: " + message);
    }

}