package com.messenger.publisher.kafka;

import com.messenger.publisher.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    MessageService messageService;

    @KafkaListener(topics = "direct", groupId = "private")
    public void listenMessage(String message) {
//        greetingService.sendToSpecificUser();

        System.out.println("Received message: " + message);
    }

}