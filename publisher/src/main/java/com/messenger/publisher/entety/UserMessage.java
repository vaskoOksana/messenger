package com.messenger.publisher.entety;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("UserMessage")
@Data
public class UserMessage implements Serializable {

    @Id
    private String receiver;

    private List<String> text;

    public UserMessage(List<String> text, String receiver) {
        this.text = text;
        this.receiver = receiver;
    }
}

