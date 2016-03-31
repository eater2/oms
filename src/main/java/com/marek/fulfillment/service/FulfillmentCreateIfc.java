package com.marek.fulfillment.service;

import com.marek.order.domain.OrderIfc;

/**
 * Created by marek.papis on 2016-03-23.
 */
@FunctionalInterface
public interface FulfillmentCreateIfc {

    public OrderIfc process(OrderIfc order);
}
