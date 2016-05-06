package com.marek.utils.producerconsumer;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Created by marek.papis on 2016-04-15.
 */
public interface Consumer {


    CompletableFuture<String> consume(List<String> outputList, BlockingQueue<String> queue, ExecutorService executor);

    void stopThread();
}
