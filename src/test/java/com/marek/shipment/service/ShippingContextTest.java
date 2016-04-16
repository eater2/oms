package com.marek.shipment.service;

import com.marek.Application;
import com.marek.order.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by marek.papis on 2016-04-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ShippingContextTest {

    @Autowired
    private ShippingContext shippingContext;

    @Autowired
    private WarehouseShippingStrategy warehouseShippingStrategy;

    @Autowired
    private Order order;

    @Before
    public void preTest() throws Exception {
        shippingContext.setShippingContext(warehouseShippingStrategy);
    }

    @Test
    public void shouldShipOrderCorrectly() throws Exception {
        assertEquals(order, shippingContext.shipOrder(order));
    }
}