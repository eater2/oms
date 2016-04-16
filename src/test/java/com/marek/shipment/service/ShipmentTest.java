package com.marek.shipment.service;

import com.marek.Application;
import com.marek.order.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;


/**
 * Created by marek.papis on 2016-04-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ShipmentTest {


    @Autowired
    Order order;

    @Autowired
    private Shipment shipmentService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldChangeStatus() throws Exception {
        assertEquals(shipmentService.getEndProcessingStatus(), shipmentService.process(order).getOrderStatus());
    }

}