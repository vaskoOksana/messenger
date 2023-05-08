package com.messenger.consumer.api;

import com.messenger.consumer.dto.MessageDTO;
import com.messenger.consumer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageConsumerController {

    @Autowired
    MessageService consumerService;

    @PostMapping("/message")
    public String postBody(@RequestBody MessageDTO message) {
        consumerService.sendMessage(message);
        return String.format("""
            Message was sent.
            Message: %s
            Receiver: %s
            """, message.getText(), message.getReceiver());
    }

}