package com.marek.core.service;

import com.marek.order.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * Created by marek.papis on 2016-03-21.
 * [Singleton Scope Spring] example, we must have 1 instance of Event store
 */
@Service
public class EventStore {
    private static final Logger log = LoggerFactory.getLogger(EventStore.class);

    private final StampedLock stampedLock = new StampedLock();

    private final HashMap<Long, ArrayList<Order>> events = new HashMap<>();

    Set<Long> getOrdersSet() {
        return null != events.keySet() ? events.keySet() : new HashSet<>();
    }

    Collection<ArrayList<Order>> getOrders() {
        return events.values();
    }

    void reset() {
        //for test purpose only
        events.clear();
    }

    Optional<Order> getLastOrderEvent(Long orderNumber) {
        //[java8] [stampedLock]
        //If no threads are writing, and no threads have requested write access
        long stamp = stampedLock.tryOptimisticRead();
        if (events.size() > 0) {
            List<Order> orders = events.get(orderNumber);
            if (stampedLock.validate(stamp)) {
                return Optional.ofNullable(orders.get(orders.size() - 1));
            } else {
                stamp = stampedLock.readLock();
                try {
                    return Optional.ofNullable(orders.get(orders.size() - 1));
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
        } else {
            return Optional.empty();
        }
    }

    Order addEvent(Long orderNumber, Order order) throws IllegalArgumentException,IllegalStateException{
        //If no threads are reading or writing
        long stamp = stampedLock.writeLock();

        try {
            //[orElseThrow] [optional] example
            if (events.containsKey(orderNumber)) {
                Optional<Order> o = Optional.ofNullable(events.get(orderNumber).add(order) ? order : null);
                return o.orElseThrow(() -> new IllegalStateException("Order was not added correctly to eventStore,order id:" + order.getId()));
            } else {
                events.put(orderNumber, new ArrayList<>(Arrays.asList(order)));
                Optional<Order> o = Optional.ofNullable(order);
                return o.orElseThrow(() -> new IllegalArgumentException("Order provided is incorrect"));
            }

        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    @Override
    public String toString() {


        return "EventStore{" +
                events.entrySet()
                        .stream()
                        .peek(o -> log.info("###############################"))
                        .peek(o -> log.info("Order id:" + o.toString()))
                        .peek(o -> log.info("###############################"))
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList()) +
                '}';


    }
}
