package com.marek.core.service;

import com.marek.order.domain.OrderIfc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * Created by marek.papis on 2016-03-21.
 */
@Service
public class EventStore implements EventStoreIfc {

    @Autowired
    private OrderIfc order;

    private final StampedLock stampedLock = new StampedLock();

    private final HashMap<Long, ArrayList<OrderIfc>> events = new HashMap<>();

    @Override
    public Optional<Set<Long>> getOrdersSet() {
        return Optional.of(events.keySet()!=null?events.keySet():new HashSet<>());
    }

    @Override
    public void reset() {
        //for test purpose only
        events.clear();
    }

        @Override
    public Optional<OrderIfc> getLastOrderEvent(Long orderNumber) {
        //If no threads are writing, and no threads have requested write access
        long stamp = stampedLock.tryOptimisticRead();
        List<OrderIfc> orders = events.get(orderNumber);

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
    }

    @Override
    public Optional<OrderIfc> addEvent(Long orderNumber, OrderIfc order) {
        //If no threads are reading or writing
        long stamp = stampedLock.writeLock();

        try {
            if (events.containsKey(orderNumber)) {
                return Optional.ofNullable(events.get(orderNumber).add(order)?order:null);
            } else {
                events.put(orderNumber,new ArrayList<>(Arrays.asList(order)));
                return Optional.of(order);
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
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()) +
                '}';
    }
}
