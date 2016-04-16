package com.marek.shipment.service;

import com.marek.order.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by marek.papis on 2016-04-12.
 */
@Component
public class WarehouseShippingStrategy implements ShippingStrategy {
    private static final Logger log = LoggerFactory.getLogger(ShippingContext.class);

    @Override
    public Order shipping(Order order) {
        log.info("Initited Shipping from Warehouse");
        return order.copyFrom(order);
    }

}
