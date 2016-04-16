package com.marek.fulfillment.service;

import com.marek.core.service.EventProcessingIfc;
import com.marek.order.domain.Order;
import com.marek.order.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by marek.papis on 2016-03-23.
 */
@Service
public class FulfillmentCreate implements EventProcessingIfc {
    private static final Logger log = LoggerFactory.getLogger(FulfillmentCreate.class);

    @Override
    public Order process(Order order) {
        try {
            log.info("order id:"+order.getId()+" processing fulfillment");
            return order.copyFrom(order, getEndProcessingStatus());
        } catch (Exception e) {
            log.error(e.getMessage() + e);
            return order.copyFrom(order, getErrorProcessingStatus());
        }
    }

    @Override
    public OrderStatus getStartProcessingStatus() {
        return OrderStatus.FULFILLMENT_START;
    }

    @Override
    public OrderStatus getEndProcessingStatus() {
        return OrderStatus.FULFILLMENT_END;
    }

    @Override
    public OrderStatus getErrorProcessingStatus() {
        return OrderStatus.FULFILLMENT_ERROR;
    }
}
