package com.marek.fulfillment.service;

import com.marek.Application;
import com.marek.order.domain.OrderIfc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by marek.papis on 2016-03-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class fulfillmentCreateTest {

    @Autowired
    OrderIfc order;

    @Autowired
    FulfillmentCreateIfc fulfillment;

    @Test
    public void processShouldChangeStatus() throws Exception {
        assertTrue(fulfillment.process(order).getOrderStatus().name().contains("_END"));
    }
}