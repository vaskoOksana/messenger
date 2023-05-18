package com.messenger.publisher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.publisher.dto.MessageDTO;
import com.messenger.publisher.dto.WebSocketMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
public class WebSocketMessageHandler extends TextWebSocketHandler {

    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    UserMessageStorageService userMessageStorageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        webSocketSessions.add(session);
        sendUserUnsentMessages(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessions.remove(session);
    }

    public boolean sendMessage(MessageDTO message) {
        String receiver = message.getReceiver();
        AtomicBoolean isMessageSent = new AtomicBoolean(false);
        webSocketSessions.stream()
            .filter(session -> isSessionOpenForSpecificReceiver(session, receiver))
            .findFirst()
            .ifPresent(session -> {
                boolean isSent = sendMessageToSpecificReceiver(session, message.getText(), receiver);
                isMessageSent.set(isSent);
            });
        return isMessageSent.get();
    }

    private boolean isSessionOpenForSpecificReceiver(WebSocketSession session, String receiverName) {
        return Objects.nonNull(session.getPrincipal()) &&
            Objects.nonNull(session.getPrincipal().getName()) &&
            session.getPrincipal().getName().equals(receiverName);
    }

    private boolean sendMessageToSpecificReceiver(WebSocketSession session, String messageText, String receiver) {
        try {
            TextMessage websocketMessage = createWebSocketMessage(messageText);
            if (Objects.nonNull(websocketMessage)) {
                session.sendMessage(websocketMessage);
                log.info("WebSocket message was sent to {}, message: {}", receiver, messageText);
                return true;
            }
            log.warn("WebSocket message is null");
            return false;
        } catch (IOException e) {
            log.error("WebSocket message was not sent to {}: {}", receiver, e.getMessage());
            return false;
        }
    }

    private TextMessage createWebSocketMessage(String messageText) {
        try {
            String json = new ObjectMapper().writeValueAsString(new WebSocketMessageDTO(messageText));
            return new TextMessage(json);
        } catch (JsonProcessingException e) {
            log.error("Message Json Processing was failed {}", e.getMessage());
            return null;
        }
    }

    private void sendUserUnsentMessages(WebSocketSession session) {
        if (Objects.isNull(session.getPrincipal()) || Objects.isNull(session.getPrincipal().getName())) {
            log.error("Session does not contain user name");
            return;
        }
        userMessageStorageService.getUserMessages(session.getPrincipal().getName()).ifPresent(userMessage -> {
            List<String> unsentMessages = userMessage.getText();
            if (!CollectionUtils.isEmpty(unsentMessages)) {
                unsentMessages.forEach(messageText -> sendMessageToSpecificReceiver(session, messageText, session.getPrincipal().getName()));
            }
            userMessageStorageService.deleteUserMessage(userMessage);
        });
    }

}