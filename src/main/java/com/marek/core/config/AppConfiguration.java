package com.marek.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.*;

/**
 * Created by marek.papis on 2016-05-07.
 */
@Configuration
public class AppConfiguration {

    @Bean(name = "taskExecutor")
    @Scope("prototype")
    public ExecutorService getExecutor(
            @Value("${array_size}") int ARRAY_SIZE,
            @Value("${thread_pool_threads_initial}")
                    int thread_pool_threads_initial,
            @Value("${thread_pool_threads_max}")
                    int thread_pool_threads_max,
            @Value("${thread_pool_timeout}")
                    long thread_pool_timeout
    ) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(ARRAY_SIZE);
        return new ThreadPoolExecutor(thread_pool_threads_initial, thread_pool_threads_max,
                thread_pool_timeout, TimeUnit.MILLISECONDS,
                queue);
    }

}
