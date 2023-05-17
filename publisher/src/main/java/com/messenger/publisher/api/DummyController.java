package com.messenger.publisher.api;

import com.messenger.publisher.dto.MessageDTO;
import com.messenger.publisher.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @Autowired
    MessageService messageService;

    @GetMapping("/message")
    public void postBody(@RequestParam String message, @RequestParam String receiver) {
        messageService.sendToSpecificUser(MessageDTO.builder().text(message).receiver(receiver).build());
    }

}