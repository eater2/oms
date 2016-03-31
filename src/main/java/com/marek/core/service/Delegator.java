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

    private FulfillmentCreateIfc fulfillment;

    private EventStoreIfc eventStore;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Autowired
    OrderIfc orderFulfillmentStart;
    @Autowired
    OrderIfc orderFulfillmentEnd;
    @Autowired
    OrderIfc orderFulfillmentError;

    @Autowired
    public Delegator(EventStoreIfc eventStore,FulfillmentCreateIfc fulfillment) {
        this.eventStore = eventStore;
        this.fulfillment = fulfillment;
    }

    @Override
    public int delegate() {
        int ordersProcessed = 0;

        //eventStore.getOrdersSet().get().stream().map()

        log.info("delegate 0");

        eventStore.getOrdersSet()

        for (Long orderNbr : eventStore.getOrdersSet()) {
            log.info("delegate 1,Key:" + orderNbr);
            if (eventStore.getLastOrderEvent(orderNbr).isPresent()) {
                log.info("delegate 2,Key:" + orderNbr);
                OrderIfc orderNew = eventStore.getLastOrderEvent(orderNbr).get();
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
            orderFulfillmentStart.copyFrom(order,OrderStatusEnum.FULFILLMENT_START);
            eventStore.addEvent(orderNbr, orderFulfillmentStart);

            orderFulfillmentEnd.copyFrom(order,OrderStatusEnum.FULFILLMENT_END);
            eventStore.addEvent(orderNbr, orderFulfillmentEnd);

            //[java8] [CompletableFuture]
            //TODO add a compatablefuture
            //CompletableFuture<OrderIfc> future = CompletableFuture.supplyAsync(() -> fulfillment.process(orderNew), executor);

            log.info("Successfully processed fulfillment order nbr: " + orderNbr);
            return true;
        } catch (Exception e) {
            log.error("exception:" + e);
            orderFulfillmentError.copyFrom(order,OrderStatusEnum.FULFILLMENT_ERROR);
            eventStore.addEvent(orderNbr, orderFulfillmentError);
            return false;
        }

    }

}
