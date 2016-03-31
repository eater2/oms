package com.marek.core.service;

import com.marek.fulfillment.service.FulfillmentCreateIfc;
import com.marek.order.domain.OrderIfc;
import com.marek.order.domain.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by marek.papis on 2016-03-23.
 */
@Service
public class Delegator implements DelegatorIfc {
    private static final Logger log = LoggerFactory.getLogger(Delegator.class);

    @Autowired
    FulfillmentCreateIfc fulfillment;

    @Autowired
    OrderIfc orderNew;

    @Autowired
    OrderIfc orderFulfillmentStart;

    @Autowired
    OrderIfc orderFulfillmentEnd;

    @Autowired
    OrderIfc orderFulfillmentError;

    private EventStoreIfc eventStore;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void setEventStore(EventStoreIfc eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public int delegate() {
        int ordersProcessed = 0;

        eventStore.getOrdersSet().get().stream().map()

        for (Long orderNbr : eventStore.getOrdersSet().get()) {
            log.info("delegate 1,Key:" + orderNbr);
            if (eventStore.getLastOrderEvent(orderNbr).isPresent()) {
                log.info("delegate 2,Key:" + orderNbr);
                orderNew = eventStore.getLastOrderEvent(orderNbr).get();
                if (orderNew.getOrderStatus() == OrderStatusEnum.INSERTED_END) {
                    log.info("delegate 3,Key:" + orderNbr);
                    ordersProcessed = fulfillmentProcess(orderNbr, orderNew)?++ordersProcessed:ordersProcessed;
                }
            }
        }
        return ordersProcessed;
    }

    private boolean fulfillmentProcess(Long orderNbr, OrderIfc order) {
        try {
            orderFulfillmentStart.copyFrom(order).setOrderStatus(OrderStatusEnum.FULFILLMENT_START);
            eventStore.addEvent(orderNbr, orderFulfillmentStart);

            orderFulfillmentEnd.copyFrom(fulfillment.process(orderFulfillmentStart)).setOrderStatus(OrderStatusEnum.FULFILLMENT_END);
            eventStore.addEvent(orderNbr, orderFulfillmentEnd);

            CompletableFuture<OrderIfc> future = CompletableFuture.supplyAsync(() -> fulfillment.process(orderNew), executor);

            log.info("Successfully processed fulfillment order nbr: " + orderNbr);
            return true;
        } catch (Exception e) {
            log.error("exception:" + e);
            orderFulfillmentError = order;
            orderFulfillmentError.setOrderStatus(OrderStatusEnum.FULFILLMENT_ERROR);
            eventStore.addEvent(orderNbr, orderFulfillmentError);
            return false;
        }

    }

}
