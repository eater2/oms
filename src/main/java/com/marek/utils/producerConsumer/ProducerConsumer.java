package com.marek.utils.producerConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

/**
 * Created by marek.papis on 2016-04-15.
 */


@Service
public class ProducerConsumer implements Consumer {
    private static final int QUEUE_SIZE = 10;
    BlockingQueue<String> queue = new ArrayBlockingQueue<String>(QUEUE_SIZE);
    private Logger log = LoggerFactory.getLogger(ProducerConsumer.class);
    private volatile boolean keepGoing = true;

    Thread produce(List<String> inputList, long sleepTime) {
        return new Thread(() -> {
            inputList
                    .stream()
                    .filter(m->keepGoing)
                    .peek(m -> {
                        try {
                            queue.put(m);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    })
                    .forEach(m -> log.info("producer processed " + m))
            ;
        }
        );
    }

    Thread consume(List<String> outputList, long sleepTime) {
        return new Thread(() -> {
            while (keepGoing)
                try {
                    outputList.add(queue.take());
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
        );
    }

    void stopThreads() {
        System.out.println("stopping Threads");
        System.out.println("queue: " + queue.toString());
        keepGoing = false;

    }

}
