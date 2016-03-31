package com.marek.order.domain;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by marek.papis on 2016-03-17.
 */
public interface OrderIfc {

    public long getId();

    public String getOrderDescription();

    public void setOrderDescription(String orderDescription);

    public List<String> getItemList();

    public void setItemList(List<String> itemList);

    public int getOrderSequence();

    public OrderStatusEnum getOrderStatus();

    public OrderIfc setOrderStatus(OrderStatusEnum orderStatus) ;

    public OrderIfc copyFrom(OrderIfc order);

    public List<OrderIfc> filterOrderList(List<OrderIfc> orders, Predicate<OrderIfc> predicate);

}
