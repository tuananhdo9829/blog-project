package com.tuananhdo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@PropertySource("classpath:async.properties")
@EnableAsync
public class AsyncConfiguration {

    @Value("${async.corePoolSize}")
    private int corePoolSize;
    @Value("${async.maxPoolSize}")
    private int maxPoolSize;
    @Value("${async.queueCapacity}")
    private int queueCapacity;
    @Value("${async.threadNamePrefix}")
    private String threadNamePrefix;

    @Bean(name = "exportThreadPool")
    public Executor executorThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}
