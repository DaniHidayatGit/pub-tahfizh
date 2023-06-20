package com.tugasakhir.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class GlobalConfig {
    @Bean
    Executor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
