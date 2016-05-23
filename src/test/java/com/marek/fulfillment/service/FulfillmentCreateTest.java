package com.marek.fulfillment.service;

import com.marek.Application;
import com.marek.order.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by marek.papis on 2016-03-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class FulfillmentCreateTest {

    @Autowired
    Order order;

    @Autowired
    FulfillmentCreate fulfillment;

    @Test
    public void shouldChangeStatus() throws Exception {
        assertEquals(fulfillment.getEndProcessingStatus(),fulfillment.process(order).getOrderStatus());
    }
}