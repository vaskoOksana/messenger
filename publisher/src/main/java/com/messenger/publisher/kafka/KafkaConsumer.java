package com.messenger.publisher.kafka;

import com.messenger.publisher.dto.MessageDTO;
import com.messenger.publisher.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    MessageService messageService;

    @KafkaListener(topics = "direct", groupId = "private", containerFactory = "messageKafkaListenerContainerFactory")
    public void listenMessage(MessageDTO message) {
        if (Objects.isNull(message)) {
            log.warn("Received message is null");
            return;
        }
        log.info("Received message: {}, receiver: {}", message.getText(), message.getReceiver());
        messageService.sendToSpecificUser(message);
    }

}