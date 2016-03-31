package com.marek.order.domain;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by marek.papis on 2016-03-17.
 */
public interface OrderIfc {

    OrderIfc copyFrom(long id, OrderStatusEnum orderStatus, String orderDescription, List<String> itemList);

    OrderIfc copyFrom(OrderIfc order, OrderStatusEnum orderStatus);

    OrderIfc copyFrom(OrderIfc order);

    public long getId();

    public String getOrderDescription();

    public List<String> getItemList();

    public int getOrderSequence();

    public OrderStatusEnum getOrderStatus();

    public List<OrderIfc> filterOrderList(List<OrderIfc> orders, Predicate<OrderIfc> predicate);

}
