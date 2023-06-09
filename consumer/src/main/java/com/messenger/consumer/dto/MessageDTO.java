package com.messenger.consumer.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private String text;
    private String receiver;

    public MessageDTO(String text, String receiver) {
        this.text = text;
        this.receiver = receiver;
    }
}
