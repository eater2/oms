package com.marek.inventory.service;

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
public class InventoryCheckTest {

    Order order;

    @Autowired
    InventoryCheck inventoryCheckService;

    @Before
    public void setUp() throws Exception {
        order = new Order();
    }

    @Test
    public void shouldChangeStatus() throws Exception {
        assertThat(inventoryCheckService.getEndProcessingStatus()).isEqualTo(inventoryCheckService.process(order).getOrderStatus());
    }

}