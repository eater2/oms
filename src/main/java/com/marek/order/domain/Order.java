package com.marek.order.domain;

import com.marek.fulfillment.domain.Fulfillment;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Order Domain class
 * Created by marek.papis on 2016-03-17.
 */
@Entity
@Table(name = "order_head")
public class Order implements Comparable<Order>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Size(max = 14)
    @Column(name = "orderDescription", length = 14)
    private String orderDescription = "";
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.INSERTED_END;
    private boolean isProcessed = false;
    @Transient
    private List<String> itemList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "fulfillment_id") //PK of foregin table
    private Fulfillment fulfillment;

    public Order() {
        //empty
    }

    public Order(OrderStatus orderStatus, String orderDescription, List<String> itemList) {
        this.orderStatus = orderStatus;
        this.orderDescription = orderDescription;
        //[java8] [Collectors] deep copy the list of strings
        this.itemList = itemList.stream().collect(Collectors.toList());

    }

    public Fulfillment getFulfillment() {
        return fulfillment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Order))
            return false;

        Order order1 = (Order) o;

        if (id != order1.id)
            return false;
        if (isProcessed != order1.isProcessed)
            return false;
        if (orderDescription != null ? !orderDescription.equals(order1.orderDescription) : order1.orderDescription != null)
            return false;
        if (orderStatus != order1.orderStatus)
            return false;
        if (itemList != null ? !itemList.equals(order1.itemList) : order1.itemList != null)
            return false;
        return fulfillment != null ? fulfillment.equals(order1.fulfillment) : order1.fulfillment == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (orderDescription != null ? orderDescription.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (isProcessed ? 1 : 0);
        result = 31 * result + (itemList != null ? itemList.hashCode() : 0);
        result = 31 * result + (fulfillment != null ? fulfillment.hashCode() : 0);
        return result;
    }

    public Order copyFrom(long id, OrderStatus orderStatus, String orderDescription, List<String> itemList) {
        Order order1 = new Order();
        order1.id = id;
        order1.orderStatus = orderStatus;
        order1.orderDescription = orderDescription;
        //[java8] [Collectors] deep copy the list of strings
        order1.itemList = itemList.stream().collect(Collectors.toList());
        return order1;
    }

    public Order copyFrom(Order order, OrderStatus orderStatus) {
        Order order1 = copyFrom(order);
        order1.orderStatus = orderStatus;
        return order1;
    }

    public Order copyFrom(Order order, OrderStatus orderStatus, boolean isProcessed) {
        Order order1 = copyFrom(order);
        order1.orderStatus = orderStatus;
        order1.isProcessed = isProcessed;
        return order1;
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

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public List<String> getItemList() {
        return null == itemList ? new ArrayList<>() : itemList;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public boolean isProcessed() {
        return this.isProcessed;
    }

    public List<Order> filterOrderList(List<Order> orders, Predicate<Order> predicate) {
        return orders.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public int compareTo(Order o) {
        return this.id < o.id ? -1 : this.id > o.id ? 1 : 0;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDescription='" + orderDescription + '\'' +
                ", orderStatus=" + orderStatus +
                ", isProcessed=" + isProcessed +
                ", itemList=" + itemList +
                ", fulfillment=" + fulfillment +
                '}';
    }
}
