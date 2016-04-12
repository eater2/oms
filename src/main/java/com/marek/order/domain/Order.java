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
public class Order {

    private long id;

    private static final AtomicInteger INITIAL_ORDER_SEQUENCE = new AtomicInteger(0);
    private static AtomicInteger orderSequence = INITIAL_ORDER_SEQUENCE;

    private String orderDescription;

    private OrderStatusEnum orderStatus;

    private List<String> itemList = new ArrayList<>();

    public Order() {
        this.id = orderSequence.incrementAndGet();
    }

    public Order copyFrom(long id,OrderStatusEnum orderStatus, String orderDescription, List<String> itemList) {
        Order order1 = new Order();
        order1.id = id;
        order1.orderStatus = orderStatus;
        order1.orderDescription = orderDescription;
        //[java8] [Collectors] deep copy the list of strings
        order1.itemList = itemList.stream().collect(Collectors.toList());
        return order1;
    }

    public Order copyFrom(Order order, OrderStatusEnum orderStatus) {
        Order order1 = copyFrom(order);
        order1.orderStatus = orderStatus;
        return  order1;
    }

    public Order copyFrom(Order order) {
        Order order1 = new Order();
        order1.id = order.getId();
        //[java8] [Collectors] deep copy the list of strings
        order1.itemList = order.getItemList().stream().collect(Collectors.toList());
        order1.orderDescription = order.getOrderDescription();
        order1.orderStatus = order.getOrderStatus();
        return order1;
    }

    public long getId() {
        return id;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public List<String> getItemList() {
        return null==itemList?new ArrayList<>():itemList;
    }

    public int getOrderSequence() {
        return orderSequence.intValue();
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public List<Order> filterOrderList(List<Order> orders, Predicate<Order> predicate){
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
