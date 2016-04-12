package com.marek.core.service;

import com.marek.order.domain.Order;
import com.marek.order.domain.OrderStatusEnum;

/**
 * Created by marek.papis on 2016-04-06.
 */
public interface EventProcessingIfc {
    public Order process(Order order);
    public OrderStatusEnum getStartProcessingStatus();
    public OrderStatusEnum getEndProcessingStatus();
    public OrderStatusEnum getErrorProcessingStatus();
}
