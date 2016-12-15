package com.marek.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import java.util.concurrent.*;

/**
 * Created by marek.papis on 2016-05-07.
 */
@Configuration
public class AppConfiguration {

    @Autowired
    Environment env;

    @Bean(name = "taskExecutor")
    @Scope("prototype")
    public ExecutorService getExecutor() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(Integer.valueOf(env.getProperty("threads.array_size")));
        return new ThreadPoolExecutor(
                Integer.valueOf(env.getProperty("threads.thread_pool_threads_initial")),
                Integer.valueOf(env.getProperty("threads.thread_pool_threads_max")),
                Integer.valueOf(env.getProperty("threads.thread_pool_timeout")),
                TimeUnit.MILLISECONDS,
                queue);
    }
}
