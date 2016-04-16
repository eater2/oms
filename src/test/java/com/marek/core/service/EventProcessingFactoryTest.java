package com.marek.core.service;

import com.marek.Application;
import com.marek.fulfillment.service.FulfillmentCreate;
import com.marek.order.domain.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;


/**
 * Created by marek.papis on 2016-04-08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EventProcessingFactoryTest {

    @Autowired
    FulfillmentCreate fulfillmentCreate;

    @Autowired
    EventProcessingFactory eventProcessingFactory;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldReturnFulfillmentService() throws Exception {
        assertEquals(fulfillmentCreate,eventProcessingFactory.createEventProcessing(OrderStatus.INVENTORY_END));
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowRuntimeException() throws Exception {
        eventProcessingFactory.createEventProcessing(OrderStatus.FULFILLMENT_START);
    }
}