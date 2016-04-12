package com.marek.shipment.service;

import com.marek.Application;
import com.marek.order.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by marek.papis on 2016-04-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class OrderShipTest {


    @Autowired
    Order order;

    @Autowired
    private OrderShip orderShipService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldChangeStatus() throws Exception {
        assertEquals(orderShipService.getEndProcessingStatus(),orderShipService.process(order).getOrderStatus());
    }
}