package com.marek.core.service;

import com.marek.order.domain.Order;
import com.marek.order.domain.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final int ARRAY_SIZE = 10;
    private static final int THREAD_POOL_THREADS_INITIAL = 1;
    private static final int THREAD_POOL_THREADS_MAX = 3;
    private static final long THREAD_POOL_TIMEOUT = 0L;
    private final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(ARRAY_SIZE);
    private EventProcessingFactory eventProcessFactory;
    private EventStore eventStore;
    private ExecutorService executor;


    @Autowired
    public Delegator(EventStore eventStore, EventProcessingFactory eventProcessFactory) {
        this.eventStore = eventStore;
        this.eventProcessFactory = eventProcessFactory;
        this.executor = new ThreadPoolExecutor(THREAD_POOL_THREADS_INITIAL, THREAD_POOL_THREADS_MAX,
                THREAD_POOL_TIMEOUT, TimeUnit.MILLISECONDS,
                queue);
    }

    public CompletableFuture<Void> delegate() {
        //loop only on last order's events , in defined statuses, returns Future of all Futures
        //[java8] [stream] [fiter] [foreach] [Array search match] example
        log.info("#############################################################");

        List<CompletableFuture<Order>> listOfFutures = eventStore.getOrders()
                .stream()
                .map(o -> o.get(o.size() - 1))
                .filter(o -> eventProcessFactory.getListOfEvents()
                        .stream()
                        .anyMatch(o.getOrderStatus()::equals))
                .peek(o -> log.info(o.toString()))
                .map(o -> eventProcess(o.getId(), o.getOrderStatus(), o))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(listOfFutures.toArray(new CompletableFuture[listOfFutures.size()]));
    }

    private CompletableFuture<Order> eventProcess(Long orderNbr, OrderStatusEnum orderStatus, Order order) throws IllegalThreadStateException {

        //[Strategy design pattern ]
        //[Factory design Pattern]
        EventProcessingIfc eventProcessing = eventProcessFactory.createEventProcessing(orderStatus);

        try {
            //lock the status on the order, for other threads not to interfere
            Order order1 = order.copyFrom(order, eventProcessing.getStartProcessingStatus());
            Order order2 = eventStore.addEvent(orderNbr, order1).get();

            //[java8] [CompletableFuture]
            return CompletableFuture.supplyAsync(() ->
                            eventProcessing.process(order2),
                    executor)
                    .thenApply(o -> o.copyFrom(o, eventProcessing.getEndProcessingStatus()))
                    .thenApply(o -> eventStore.addEvent(orderNbr, o).get())
                    ;

        } catch (RejectedExecutionException r) {
            log.error("exception: " + r);
            eventStore.addEvent(orderNbr, order.copyFrom(order, eventProcessing.getErrorProcessingStatus()));
            throw new IllegalThreadStateException("exception on thread's execution:" + r.toString());
        } catch (Exception e) {
            log.error("exception:" + e);
            eventStore.addEvent(orderNbr, order.copyFrom(order, eventProcessing.getErrorProcessingStatus()));
            throw new IllegalThreadStateException("exception on thread's execution:" + e.toString());
        }

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
