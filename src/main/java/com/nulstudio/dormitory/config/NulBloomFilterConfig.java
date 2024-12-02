package com.nulstudio.dormitory.config;

import io.rebloom.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NulBloomFilterConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public Client redisBloomClient() {
        return new Client(host, port);
    }
}
