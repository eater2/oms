package com.marek.order.domain;

/**
 * Created by marek.papis on 2016-03-17.
 */
public enum OrderStatus {
    INSERTED_END,
    FULFILLMENT_START,
    FULFILLMENT_END,
    FULFILLMENT_ERROR,
    INVENTORY_START,
    INVENTORY_END,
    INVENTORY_ERROR,
    SHIP_START,
    SHIP_END,
    PROCESSING_ERROR, SHIP_ERROR
}
