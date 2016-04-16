package com.marek.inventory.service;

import com.marek.core.service.EventProcessingIfc;
import com.marek.order.domain.Order;
import com.marek.order.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by marek.papis on 2016-04-02.
 */
@Service
public class InventoryCheck implements EventProcessingIfc {

    private static final Logger log = LoggerFactory.getLogger(InventoryCheck.class);

    @Override
    public Order process(Order order) {
        try {
            log.info("order id:"+order.getId()+" processing inventory check");
            return order.copyFrom(order, getEndProcessingStatus());
        } catch (Exception e) {
            log.error(e.getMessage() + e);
            return order.copyFrom(order, getErrorProcessingStatus());
        }
    }

    @Override
    public OrderStatus getStartProcessingStatus() {
        return OrderStatus.INVENTORY_START;
    }

    @Override
    public OrderStatus getEndProcessingStatus() {
        return OrderStatus.INVENTORY_END;
    }

    @Override
    public OrderStatus getErrorProcessingStatus() {
        return OrderStatus.INVENTORY_ERROR;
    }

}
