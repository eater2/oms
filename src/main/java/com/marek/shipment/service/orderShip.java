package com.marek.shipment.service;

import com.marek.core.service.EventProcessingIfc;
import com.marek.order.domain.Order;
import com.marek.order.domain.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by marek.papis on 2016-04-06.
 */
@Service
public class OrderShip implements EventProcessingIfc {
    private static final Logger log = LoggerFactory.getLogger(OrderShip.class);

    @Override
    public Order process(Order order) {
        try {
            log.info("order id:"+order.getId()+" processing Shipment");
            return order.copyFrom(order, getEndProcessingStatus());
        } catch (Exception e) {
            log.error(e.getMessage() + e);
            return order.copyFrom(order, getErrorProcessingStatus());
        }
    }

    @Override
    public OrderStatusEnum getStartProcessingStatus() {
        return OrderStatusEnum.SHIP_START;
    }

    @Override
    public OrderStatusEnum getEndProcessingStatus() {
        return OrderStatusEnum.SHIP_END;
    }

    @Override
    public OrderStatusEnum getErrorProcessingStatus() {
        return OrderStatusEnum.SHIP_ERROR;
    }
}
