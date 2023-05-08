package com.messenger.publisher.service;

import com.messenger.publisher.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Value(value = "${websocket.broker}")
    private String broker;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void sendToSpecificUser(MessageDTO message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), broker, new MessageDTO(message.getText()));
    }

}