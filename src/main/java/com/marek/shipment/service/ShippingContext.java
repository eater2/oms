package com.marek.shipment.service;

import com.marek.order.domain.Order;
import org.springframework.stereotype.Service;

/**
 * Created by marek.papis on 2016-04-12.
 */
@Service
class ShippingContext {
    private ShippingStrategy shippingStrategy;

    ShippingContext setShippingContext(ShippingStrategy shippingStrategy) {
        this.shippingStrategy = shippingStrategy;
        return this;
    }

    Order shipOrder(Order order) {
        return shippingStrategy.shipping(order);
    }
}
