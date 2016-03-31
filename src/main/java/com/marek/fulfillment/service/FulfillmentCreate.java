package com.marek.fulfillment.service;

import com.marek.Application;
import com.marek.order.domain.OrderIfc;
import com.marek.order.domain.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by marek.papis on 2016-03-23.
 */
@Service
public class FulfillmentCreate implements FulfillmentCreateIfc {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Override
    public OrderIfc process(OrderIfc order) {
        try {
            order.copyFrom(order,OrderStatusEnum.FULFILLMENT_END);
            return order;
        }
        catch (Exception e)
        {
            log.error(e.getMessage()+e);
            order.copyFrom(order,OrderStatusEnum.FULFILLMENT_ERROR);
            return order;
        }
    }
}
