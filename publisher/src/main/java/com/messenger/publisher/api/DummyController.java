package com.messenger.publisher.api;

import com.messenger.publisher.dto.Message;
import com.messenger.publisher.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @Autowired
    MessageService serv;

    @GetMapping("/message")
    public void postBody(@RequestParam String name, @RequestParam String message) {
        serv.sendToSpecificUser(name, Message.builder().text(message).build());
    }

}