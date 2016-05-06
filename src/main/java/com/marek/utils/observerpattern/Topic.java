package com.marek.utils.observerpattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by marek.papis on 2016-04-21.
 */
@Service
public class Topic {
    private static final int QUEUE_SIZE = 10;
    Logger log = LoggerFactory.getLogger(SubscriberImpl.class);
    private Queue<String> topic = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private Queue<Subscriber> subscribers = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    ;

    boolean addMessage(String message) {
        topic.add(message);
        notifySubscribers(message);
        return true;
    }

    boolean removeMessage(String message) {
        return topic.remove(message);
    }

    synchronized boolean notifySubscribers(String message) {
        List<CompletableFuture<String>> listOfCf =
                subscribers.stream()
                        .peek(e -> log.info(e.toString()))
                        .map(s -> {
                                    return CompletableFuture.supplyAsync(
                                            () -> s.update(message)
                                            , executorService
                                    );
                                }
                        ).collect(Collectors.toList());
        CompletableFuture<Void> voidCF = CompletableFuture.allOf(listOfCf.toArray(new CompletableFuture[listOfCf.size()]));
        try {
            voidCF.get();
            removeMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return true;
    }

    boolean addSubscriber(Subscriber subscriber) {
        return subscribers.add(subscriber);
    }

    boolean removeSubscriber(Subscriber subscriber) {
        return subscribers.remove(subscriber);
    }

    public boolean preDestroy() throws IllegalThreadStateException {
        executorService.shutdown();
        final boolean done;
        try {
            done = executorService.awaitTermination(1, TimeUnit.MINUTES);
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
