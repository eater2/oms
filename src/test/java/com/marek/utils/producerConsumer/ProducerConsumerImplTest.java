package com.marek.utils.producerconsumer;

import com.marek.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marek.papis on 2016-04-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ProducerConsumerImplTest {
    private static final int QUEUE_SIZE = 10;
    private static final int THREAD_POOL_THREADS_INITIAL = 1;
    private static final int THREAD_POOL_THREADS_MAX = 3;
    private static final long THREAD_POOL_TIMEOUT = 0L;
    private final String MESSAGE1 = "Message1";
    private final long SLEEP_TIME = 1000;
    BlockingQueue<String> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    BlockingQueue<Runnable> executorQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    ExecutorService executorService;
    @Autowired
    ConsumerImpl consumerImpl;
    @Autowired
    ProducerImpl producerImpl;

    private Logger log = LoggerFactory.getLogger(ConsumerImpl.class);
    private List<String> inputList = new ArrayList<>();
    private List<String> outputList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        //prepare input list messages
        IntStream.range(1, 10).forEach(i -> inputList.add("Message" + i));
        executorService = new ThreadPoolExecutor(THREAD_POOL_THREADS_INITIAL, THREAD_POOL_THREADS_MAX,
                THREAD_POOL_TIMEOUT, TimeUnit.MILLISECONDS,
                executorQueue);
    }

    @After
    public void tearDown() throws Exception {
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
    }
    @Ignore
    @Test
    public void shouldPutMessageOnQueue() throws Exception {
        CompletableFuture<String> cfp = producerImpl.produce(inputList, queue, executorService);


        producerImpl.stopThread();
        cfp.get();

        System.out.println("Input queue:" + inputList.toString());
        System.out.println("Intermediate queue:" + queue.toString());
        assertTrue(queue.contains(MESSAGE1));
    }
    @Ignore
    @Test
    public void shouldTakeMessageFromQueue() throws Exception {
        CompletableFuture<String> cfp = producerImpl.produce(inputList, queue, executorService);
        CompletableFuture<String> cfc = consumerImpl.consume(outputList, queue, executorService);


        producerImpl.stopThread();
        consumerImpl.stopThread();

        cfp.get();
        cfc.get();

        System.out.println("Input queue:" + inputList.toString());
        System.out.println("Intermediate queue:" + queue.toString());
        System.out.println("Output queue:" + outputList.toString());

        assertFalse(queue.contains(MESSAGE1));
        assertTrue(outputList.contains(MESSAGE1));
    }


}