package com.marek.utils.producerconsumer;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by marek.papis on 2016-04-15.
 */
public interface Producer {

    public CompletableFuture<String> produce(List<String> inputList,BlockingQueue<String> queue, ExecutorService executor);
    void stopThread();

}