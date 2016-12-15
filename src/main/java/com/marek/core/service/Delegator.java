package com.marek.core.service;

import com.marek.order.domain.Order;
import com.marek.order.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by marek.papis on 2016-03-23.
 */
@Service
public class Delegator {
    private static final Logger log = LoggerFactory.getLogger(Delegator.class);

    private EventProcessingFactory eventProcessFactory;
    private EventStore eventStore;
    private ExecutorService executor;


    @Autowired
    Delegator(EventStore eventStore,
              EventProcessingFactory eventProcessFactory,
              @Qualifier("taskExecutor") ExecutorService executor) {
        this.eventStore = eventStore;
        this.eventProcessFactory = eventProcessFactory;
        this.executor = executor;
    }

    /**
     * Loops on the Event Store,find orders in correct status and send them for processing
     *
     * @return
     */
    CompletableFuture<Void> delegate() {
        //loop only on last order's events , in defined statuses, returns Future of all Futures
        //[java8] [stream] [fiter] [foreach] [Array search match] example
        log.info("#############################################################");

        List<CompletableFuture<Order>> listOfFutures = eventStore.getOrders()
                .stream()
                .map(o -> o.get(o.size() - 1))
                .filter(o -> !o.isProcessed())
                .filter(o -> eventProcessFactory.getListOfEvents()
                        .stream()
                        .anyMatch(o.getOrderStatus()::equals))
                .peek(o -> log.info(o.toString()))
                .map(o -> produceEvent(o.getId(), o.getOrderStatus(), o))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(listOfFutures.toArray(new CompletableFuture[listOfFutures.size()]));
    }

    /**
     * process single order event : [javaDoc] example
     *
     * @param orderNbr    Order number
     * @param orderStatus Order status
     * @param order       Order instance
     * @return Completable Future of Order
     * @throws IllegalThreadStateException
     */
    private CompletableFuture<Order> produceEvent(Long orderNbr, OrderStatus orderStatus, Order order) throws IllegalThreadStateException {

        try {
            //[java8] [CompletableFuture]
            //[Producer Consumer Design Pattern] Producer example (not clear case)
            //produce the event, puts it on Executor queue for further asynchronous processing
            //[State Design Patter] 
            Order order2 = markProcessingStart(orderNbr, order);
            return CompletableFuture.supplyAsync(() ->
                            consumeEvent(orderStatus, order2),
                    executor)
                    .thenApply(o -> markProcessingEnd(orderNbr, o))
                    ;

        } catch (RejectedExecutionException r) {
            log.error("exception: " + r);
            eventStore.addEvent(orderNbr, order.copyFrom(order, OrderStatus.PROCESSING_ERROR, false));
            throw new IllegalThreadStateException("exception on thread's execution:" + r.toString());
        } catch (Exception e) {
            log.error("exception:" + e);
            eventStore.addEvent(orderNbr, order.copyFrom(order, OrderStatus.PROCESSING_ERROR, false));
            throw new IllegalThreadStateException("exception on thread's execution:" + e.toString());
        }

    }

    /**
     * [Producer Consumer Design Pattern] Consumer example
     *
     * @param orderStatus
     * @param order2
     * @return
     */
    private Order consumeEvent(OrderStatus orderStatus, Order order2) {
        //[Producer Consumer Design Pattern] Consumer example (not clear case)
        //[Factory design Pattern]
        EventProcessingIfc eventProcessing = eventProcessFactory.createEventProcessing(orderStatus);
        return eventProcessing.process(order2);
    }

    private Order markProcessingStart(Long orderNbr, Order order) {
        //lock the status on the order, for other threads not to interfere
        return eventStore.addEvent(orderNbr, order.copyFrom(order, order.getOrderStatus(), true));
    }

    private Order markProcessingEnd(Long orderNbr, Order order) {
        //we take status from processing event (cause error status might have happened there
        return eventStore.addEvent(orderNbr, order.copyFrom(order, order.getOrderStatus(), false));
    }

    @PreDestroy
    public boolean preDestroy() throws IllegalThreadStateException {
        executor.shutdown();
        final boolean done;
        try {
            done = executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.error("exception:" + e);
            //[Runtime exception rethrow]
            throw new IllegalThreadStateException("exception on pool-thread's termination:" + e.toString());
        }
        log.debug("##################################################################");
        log.debug("All thread in the pool completed ? {}", done);
        return true;
    }


}
