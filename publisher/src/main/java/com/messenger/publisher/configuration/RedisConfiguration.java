package com.messenger.publisher.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Value(value = "${spring.redis.host}")
    private String host;

    @Value(value = "${spring.redis.port}")
    private Integer port;

    @Value(value = "${spring.redis.pass}")
    private String password;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory
            = new JedisConnectionFactory();
        jedisConFactory.setHostName(host);
        jedisConFactory.setPort(port);
        jedisConFactory.setPassword(password);
        return jedisConFactory;
    }

}
