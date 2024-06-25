package com.tax.test.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class FeignConfiguration {

    @Bean
    public ExecutorService feignExecutorService(@Value("${feign.client.config.default.connection-pool}") int connectionPoolSize) {
        return Executors.newFixedThreadPool(connectionPoolSize);
    }
}
