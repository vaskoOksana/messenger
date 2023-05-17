package com.messenger.publisher.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class WebSocketMessageHandler extends TextWebSocketHandler {

    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        webSocketSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessions.remove(session);
    }

    public void handleDirectMessage(TextMessage websocketMessage, String receiver) {
        webSocketSessions.stream()
            .filter(session -> isSessionOpenForMessageReceiver(session, receiver))
            .findFirst()
            .ifPresent(session -> {
                try {
                    session.sendMessage(websocketMessage);
                    log.info("WebSocket message was sent to {}", receiver);
                } catch (IOException e) {
                    log.error("WebSocket message was not sent to {}: {}", receiver, e.getMessage());
                }
            });
    }

    private boolean isSessionOpenForMessageReceiver(WebSocketSession session, String receiverName) {
        return Objects.nonNull(session.getPrincipal()) &&
            Objects.nonNull(session.getPrincipal().getName()) &&
            session.getPrincipal().getName().equals(receiverName);
    }

}