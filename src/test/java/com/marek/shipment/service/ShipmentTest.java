package com.marek.shipment.service;

import com.marek.Application;
import com.marek.order.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by marek.papis on 2016-04-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ShipmentTest {

    private Order order;

    @Autowired
    private Shipment shipmentService;

    @Before
    public void setUp() throws Exception {
        order = new Order();
    }

    @Test
    public void shouldChangeStatus() throws Exception {
        assertThat(shipmentService.getEndProcessingStatus()).isEqualTo( shipmentService.process(order).getOrderStatus());
    }

}