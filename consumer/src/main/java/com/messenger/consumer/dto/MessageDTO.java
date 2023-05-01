package com.messenger.consumer.dto;

import lombok.Data;

@Data
public class MessageDTO {

    private String messageText;
    private String sender;
    private String receiver;
}
