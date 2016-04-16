 package com.marek.utils.producerConsumer;

import com.marek.Application;
import com.marek.utils.producerConsumer.ProducerConsumer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marek.papis on 2016-04-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ProducerConsumerTest {
    private final String MESSAGE1 = "Message1";
    private final String MESSAGE2 = "Message2";

    private final long SLEEP_TIME = 1000;
    @Autowired
    ProducerConsumer producerConsumer;
    private Logger log = LoggerFactory.getLogger(ProducerConsumer.class);
    private List<String> inputList = new ArrayList<>();
    private List<String> outputList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        IntStream.range(1,10).forEach(i->inputList.add("Message"+i));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldPutMessageOnQueue() throws Exception {
        Thread t = producerConsumer.produce(inputList, SLEEP_TIME);
        t.start();
        sleep(4*SLEEP_TIME);
        producerConsumer.stopThreads();

        System.out.println("Input queue:"+inputList.toString());
        System.out.println("Output queue:"+outputList.toString());

        assertTrue(producerConsumer.queue.contains(MESSAGE1));
    }

    @Test
    public void shouldTakeMessageFromQueue() throws Exception {
        producerConsumer.produce(inputList, SLEEP_TIME).start();
        producerConsumer.consume(outputList, SLEEP_TIME).start();
        sleep(4 * SLEEP_TIME);
        producerConsumer.stopThreads();

        System.out.println("Input queue:"+inputList.toString());
        System.out.println("Output queue:"+outputList.toString());

        assertFalse(producerConsumer.queue.contains(MESSAGE1));
        assertTrue(outputList.contains(MESSAGE1));
    }


}