package com.marek.utils.factorypattern;

import com.marek.order.domain.OrderStatus;
import com.marek.shipment.service.ShippingStrategy;
import com.marek.shipment.service.StoreShippingStrategy;
import com.marek.shipment.service.WarehouseShippingStrategy;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Optional;

/**
 * Created by marek.papis on 2016-04-20.
 */
@Service
public class FactoryPattern {
    private final EnumMap<OrderStatus, ShippingStrategy> mapp = new EnumMap<>(OrderStatus.class);

    private FactoryPattern() {
        mapp.put(OrderStatus.FULFILLMENT_START, new StoreShippingStrategy());
        mapp.put(OrderStatus.FULFILLMENT_END, new WarehouseShippingStrategy());
    }

    public ShippingStrategy getInstance(OrderStatus orderStatus) throws IllegalArgumentException {
        return Optional.ofNullable(mapp
                .get(orderStatus))
                .orElseThrow(() -> new IllegalArgumentException("Incorrect status given"));

/*      FOr [flyweight design patter]
        ShippingStrategy shippingStrategy = Optional.ofNullable(mapp
                .get(orderStatus))
                .orElse(new StoreShippingStrategy());
        mapp.put(orderStatus, shippingStrategy);
*/


    }
}


