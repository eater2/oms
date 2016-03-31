package com.marek.order.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Order Domain class
 * Created by marek.papis on 2016-03-17.
 */
@Component
@Scope("prototype")
class Order implements OrderIfc{

    private long id;

    private static final AtomicInteger INITIAL_ORDER_SEQUENCE = new AtomicInteger(0);
    private static AtomicInteger orderSequence = INITIAL_ORDER_SEQUENCE;

    private String orderDescription;

    private OrderStatusEnum orderStatus;

    private List<String> itemList = new ArrayList<>();

    public Order() {
        this.id = orderSequence.incrementAndGet();
    }

    @Override
    public OrderIfc copyFrom(long id,OrderStatusEnum orderStatus, String orderDescription, List<String> itemList) {
        this.id = id;
        //[java8] [Collectors] deep copy the list of strings
        this.itemList = itemList.stream().collect(Collectors.toList());
        this.orderDescription = orderDescription;
        this.orderStatus = orderStatus;
        return this;
    }

    @Override
    public OrderIfc copyFrom(OrderIfc order, OrderStatusEnum orderStatus) {
        copyFrom(order);
        this.orderStatus = orderStatus;
        return this;
    }

    @Override
    public OrderIfc copyFrom(OrderIfc order) {
        this.id = order.getId();
        //[java8] [Collectors] deep copy the list of strings
        this.itemList = order.getItemList().stream().collect(Collectors.toList());
        this.orderDescription = order.getOrderDescription();
        this.orderStatus = order.getOrderStatus();
        return this;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getOrderDescription() {
        return orderDescription;
    }

    @Override
    public List<String> getItemList() {
        return null==itemList?new ArrayList<>():itemList;
    }

    @Override
    public int getOrderSequence() {
        return orderSequence.intValue();
    }

    @Override
    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    @Override
    public List<OrderIfc> filterOrderList(List<OrderIfc> orders, Predicate<OrderIfc> predicate){
        return orders.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDescription='" + orderDescription + '\'' +
                ", orderStatus=" + orderStatus +
                ", itemList=" + itemList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Order order = (Order) o;

        if (id != order.id)
            return false;
        if (!orderDescription.equals(order.orderDescription))
            return false;
        if (orderStatus != order.orderStatus)
            return false;
        return itemList.equals(order.itemList);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + orderDescription.hashCode();
        result = 31 * result + orderStatus.hashCode();
        result = 31 * result + itemList.hashCode();
        return result;
    }


}
