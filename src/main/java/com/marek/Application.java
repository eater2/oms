package com.marek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    Application() {
    }

    public static void main(String[] args) {

        log.info("application started");
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);

    }
}
