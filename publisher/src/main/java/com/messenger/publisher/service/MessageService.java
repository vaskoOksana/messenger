package com.messenger.publisher.service;

import com.messenger.publisher.dto.Message;
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

    public void sendToSpecificUser(String to, Message message) {
        simpMessagingTemplate.convertAndSendToUser(to, broker, message);
    }

}