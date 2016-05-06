package com.marek.utils.observerpattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by marek.papis on 2016-04-21.
 */
@Service
@Scope("prototype")
public class SubscriberImpl implements Subscriber {
    Logger log = LoggerFactory.getLogger(SubscriberImpl.class);
    private static AtomicInteger counter = new AtomicInteger(1);
    private final String name;

    public SubscriberImpl() {
        this.name = "SubscriberImpl "+counter.incrementAndGet();
    }

    @Override
    public String update(String msg) {
        log.info(name + " " + msg);
        return msg;
    }

    @Override
    public String toString() {
        return name;
    }
}
