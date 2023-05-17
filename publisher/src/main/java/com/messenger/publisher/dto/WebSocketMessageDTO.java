package com.messenger.publisher.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class WebSocketMessageDTO {
    private String message;

    public WebSocketMessageDTO() {
    }

    public WebSocketMessageDTO(String message) {
        this.message = message;
    }
}