package com.marek.core.config;

import com.marek.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.*;

/**
 * Created by marek.papis on 2016-05-08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AppConfigurationTest {


    @Autowired
    @Qualifier("taskExecutor")
    Executor executor;

    @Autowired
    private Environment env;

    @Value("${app.name}")
    private String appName;


    @Test
    public void returnsExecutorService() throws Exception {
        assertEquals(env.getProperty("app.name"),appName);
        assertTrue(executor instanceof ExecutorService);
    }
}