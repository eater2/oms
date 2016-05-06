package com.marek.shipment.service;

import com.marek.order.domain.Order;

/**
 * Created by marek.papis on 2016-04-12.
 */
@FunctionalInterface
public interface ShippingStrategy {
    Order shipping(Order order);
}
