package com.marek.core.service;

import com.marek.order.domain.OrderIfc;

import java.util.Optional;
import java.util.Set;

/**
 * Created by marek.papis on 2016-03-22.
 */
public interface EventStoreIfc {
    Optional<OrderIfc> getLastOrderEvent(Long orderNumber);

    Optional<OrderIfc> addEvent(Long orderNumber, OrderIfc order);

    Set<Long> getOrdersSet();

    public void reset();

}
