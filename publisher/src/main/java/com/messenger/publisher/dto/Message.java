package com.messenger.publisher.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private String text;
}