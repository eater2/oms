package com.marek.shipment.service;

import com.marek.order.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by marek.papis on 2016-04-12.
 */
@Component
public class StoreShippingStrategy implements ShippingStrategy {
    private static final Logger log = LoggerFactory.getLogger(StoreShippingStrategy.class);

    @Override
    public Order shipping(Order order) {
        log.info("Initited Shipping from Store");
        order.getItemList();
        return order.copyFrom(order);
    }
}
