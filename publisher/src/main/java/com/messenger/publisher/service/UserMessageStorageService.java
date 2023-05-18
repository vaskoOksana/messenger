package com.messenger.publisher.service;

import com.messenger.publisher.dto.MessageDTO;
import com.messenger.publisher.entety.UserMessage;
import com.messenger.publisher.repositoty.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class UserMessageStorageService {

    @Autowired
    MessageRepository messageRepository;

    public Optional<UserMessage> getUserMessages(String receiver) {
        return messageRepository.findById(receiver);
    }

    public void deleteUserMessage(UserMessage userMessage) {
        messageRepository.delete(userMessage);
        log.info("All messages were removed from redis for receiver {}", userMessage.getReceiver());
    }

    public void saveUserUnsentMessage(MessageDTO message) {
        messageRepository.findById(message.getReceiver())
            .ifPresentOrElse(userMessage -> addMessageToExistingUserMessages(userMessage, message.getText()), () -> createUserMessage(message));
    }

    private void addMessageToExistingUserMessages(UserMessage userMessage, String unsentMessageText) {
        log.info("Adding unsent message to user message in redis for key: {}", userMessage.getReceiver());
        userMessage.getText().add(unsentMessageText);
        messageRepository.save(userMessage);
    }

    public void createUserMessage(MessageDTO message) {
        log.info("Creating user message in redis for key: {}", message.getReceiver());
        messageRepository.save(new UserMessage(Collections.singletonList(message.getText()), message.getReceiver()));
    }

}
