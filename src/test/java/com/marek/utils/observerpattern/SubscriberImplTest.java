package com.marek.utils.observerpattern;

import com.marek.Application;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by marek.papis on 2016-04-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class SubscriberImplTest {
    @Autowired
    SubscriberImpl subscriber;

    @Ignore
    @Test
    public void shouldGetUpdated() throws Exception {
        subscriber.update("msg1");
    }

}