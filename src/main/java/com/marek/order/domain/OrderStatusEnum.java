package com.marek.order.domain;

/**
 * Created by marek.papis on 2016-03-17.
 */
public enum OrderStatusEnum {
    INSERTED_END,
    FULFILLMENT_START,
    FULFILLMENT_END,
    FULFILLMENT_ERROR,
    INVENTORY_START,
    INVENTORY_END,
    INVENTORY_ERROR
}
