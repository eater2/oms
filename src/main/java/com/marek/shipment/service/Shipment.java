package com.marek.shipment.service;

import com.marek.core.service.EventProcessingIfc;
import com.marek.order.domain.Order;
import com.marek.order.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by marek.papis on 2016-04-06.
 */
@Service
public class Shipment implements EventProcessingIfc {
    private static final Logger log = LoggerFactory.getLogger(Shipment.class);
    private ShippingContext shippingContext;
    private StoreShippingStrategy storeShippingStrategy;
    private WarehouseShippingStrategy warehouseShippingStrategy;

    @Autowired
    public Shipment(ShippingContext shippingContext, StoreShippingStrategy storeShippingStrategy, WarehouseShippingStrategy warehouseShippingStrategy) {
        this.shippingContext = shippingContext;
        this.storeShippingStrategy = storeShippingStrategy;
        this.warehouseShippingStrategy = warehouseShippingStrategy;
    }

    @Override
    public Order process(Order order) {
        try {
            log.info("order id:"+order.getId()+" processing Shipment");

            //[Strategy design pattern]
            return shippingContext
                    .setShippingContext(order.getItemList().size()<2?storeShippingStrategy:warehouseShippingStrategy)
                    .shipOrder(order)
                    .copyFrom(order, getEndProcessingStatus());
        } catch (Exception e) {
            log.error(e.getMessage() + e);
            return order.copyFrom(order, getErrorProcessingStatus());
        }
    }

    @Override
    public OrderStatus getStartProcessingStatus() {
        return OrderStatus.SHIP_START;
    }

    @Override
    public OrderStatus getEndProcessingStatus() {
        return OrderStatus.SHIP_END;
    }

    @Override
    public OrderStatus getErrorProcessingStatus() {
        return OrderStatus.SHIP_ERROR;
    }
}
