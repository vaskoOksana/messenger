package com.messenger.consumer.service;

import com.messenger.consumer.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class MessageService {

    @Autowired
    private KafkaTemplate<String, MessageDTO> kafkaTemplate;

    @Value(value = "${spring.kafka.topic.name}")
    private String topicName;

    public void sendMessage(MessageDTO message) {
        CompletableFuture<SendResult<String, MessageDTO>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[" + message +
                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.error("Unable to send message=[" +
                    message + "] due to : " + ex.getMessage());
            }
        });
    }
}
