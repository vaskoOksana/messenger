package com.messenger.publisher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.publisher.configuration.WebSocketMessageHandler;
import com.messenger.publisher.dto.MessageDTO;
import com.messenger.publisher.dto.WebSocketMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

@Service
@Slf4j
public class MessageService {

    @Autowired
    WebSocketMessageHandler handler;

    public void sendToSpecificUser(MessageDTO message) {
        try {
            String json = new ObjectMapper().writeValueAsString(new WebSocketMessageDTO(message.getText()));
            TextMessage websocketMessage = new TextMessage(json);
            String receiver = message.getReceiver();
            handler.handleDirectMessage(websocketMessage, receiver);
        } catch (JsonProcessingException e) {
            log.error("Message was not sent to {}: {}", message.getReceiver(), e.getMessage());
        }
    }

}