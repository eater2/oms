package com.marek.utils.observerpattern;

import com.marek.Application;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by marek.papis on 2016-04-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TopicTest {
    private final String MSG1 = "msg1";
    private final String MSG2 = "msg2";

    @Autowired
    Topic topic;

    @Autowired
    SubscriberImpl subscriber1;

    @Autowired
    SubscriberImpl subscriber2;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }


    @Ignore
    @Test
    public void shouldRemoveMessage() throws Exception {
        topic.addMessage(MSG1);
    }

    @Ignore
    @Test
    public void notifySubscribers() throws Exception {
        topic.addSubscriber(subscriber1);
        topic.addSubscriber(subscriber2);
        topic.notifySubscribers(MSG1);
        assertFalse(topic.removeMessage(MSG1));
    }

    @Ignore
    @Test
    public void addSubscriber() throws Exception {
        assertTrue(topic.addSubscriber(subscriber1));
    }

    @Ignore
    @Test
    public void removeSubscriber() throws Exception {
        topic.addSubscriber(subscriber1);
        assertTrue(topic.removeSubscriber(subscriber1));

    }

    @Ignore
    @Test
    public void shouldAddMessage() throws Exception {
        assertTrue(topic.addMessage(MSG1));
        //run in the last statement
        topic.preDestroy();
    }

}