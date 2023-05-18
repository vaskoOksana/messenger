package com.messenger.publisher.service;

import com.messenger.publisher.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageService {

    @Autowired
    WebSocketMessageHandler handler;

    @Autowired
    UserMessageStorageService userMessageStorageService;

    public void sendToSpecificUser(MessageDTO message) {
        boolean isMessageSent = handler.sendMessage(message);
        if (!isMessageSent) {
            userMessageStorageService.saveUserUnsentMessage(message);
        }
    }

}