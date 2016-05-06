package com.marek.utils.factorypattern;

import com.marek.Application;
import com.marek.order.domain.OrderStatus;
import com.marek.shipment.service.StoreShippingStrategy;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by marek.papis on 2016-04-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class FactoryPatternTest {
    @Autowired
    FactoryPattern factoryPattern;

    @Ignore
    @Test
    public void shouldReturnInstance() throws Exception {
        assertTrue(factoryPattern.getInstance(OrderStatus.FULFILLMENT_START) instanceof StoreShippingStrategy);

    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnRuntimeException() throws Exception {
        factoryPattern.getInstance(OrderStatus.INSERTED_END);

    }

}