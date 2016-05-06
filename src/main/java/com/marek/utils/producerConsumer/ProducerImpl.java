package com.marek.utils.producerconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by marek.papis on 2016-04-16.
 * [Producer Consumer Design Pattern] Producer example
 */
@Service
class ProducerImpl implements Producer {
    private Logger log = LoggerFactory.getLogger(ProducerImpl.class);
    private volatile boolean keepGoing = true;
    private Thread currentThread;

    @Override
    public CompletableFuture<String> produce(List<String> inputList, BlockingQueue<String> queue, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            Thread.currentThread().setName("Producer Thread");
            this.currentThread = Thread.currentThread();
            inputList
                    .stream()
                    .filter(m -> keepGoing)
                    .peek(m -> {
                        try {
                            queue.put(m);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    })
                    .forEach(m -> log.info("producer processed message:" + m));
            log.info("Finishing Producer");
            return "Finished";
        }, executor);
    }

    @Override
    public void stopThread() {
        log.info("stopping Threads");
        keepGoing = false;
        this.currentThread.interrupt();

    }

}
