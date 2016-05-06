package com.marek.utils.producerconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by marek.papis on 2016-04-15.
 * [Producer Consumer Design Pattern] Consumer example
 */

@Service
class ConsumerImpl implements Consumer {
    private Logger log = LoggerFactory.getLogger(ConsumerImpl.class);
    private volatile boolean keepGoing = true;
    private Thread currentThread;

    @Override
    public CompletableFuture<String> consume(List<String> outputList, BlockingQueue<String> queue, ExecutorService executor) {

        return CompletableFuture.supplyAsync(() -> {
            while (keepGoing) {
                try {
                    Thread.currentThread().setName("Consumer Thread");
                    this.currentThread = Thread.currentThread();
                    String s = queue.take();//wait till there is something to take
                    outputList.add(s);
                    log.info("Consumer consumed message:" + s);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            log.info("Finishing Consumer");
            return "Finished";
        }, executor);
    }

    @Override
    public void stopThread() {
        log.info("stopping Threads");
        keepGoing = false;
        currentThread.interrupt();
    }

}
