package com.marek.utils.executearoundmethodpattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Created by marek.papis on 2016-04-20.
 */
public class ExecuteAroundMethodPattern {
    private ExecuteAroundMethodPattern() {

    }
}

class Resource {
    Logger log = LoggerFactory.getLogger(Resource.class);

    private Resource() {
        log.info("Created ..");
    }

    public static void use(Consumer<Resource> consumer) {
        Resource resource = new Resource();
        try {
            consumer.accept(resource);
        } finally {
            resource.close();
        }
        ;
    }

    public Resource op1() {
        log.info("op1");
        return this;
    }

    public Resource op2() {
        log.info("op2");
        return this;
    }

    public void close() {
        log.info("cleanup ..");
    }
}
