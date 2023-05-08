package com.messenger.publisher.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDTO {
    private String text;
    private String receiver;

    public MessageDTO(String text, String receiver) {
        this.text = text;
        this.receiver = receiver;
    }

    public MessageDTO() {
    }

    public MessageDTO(String text) {
        this.text = text;
    }
}