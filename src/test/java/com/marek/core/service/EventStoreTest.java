package com.marek.core.service;

import com.marek.Application;
import com.marek.order.domain.OrderIfc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import java.util.*;

/**
 * Created by marek.papis on 2016-03-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EventStoreTest {

    private static final Logger log = LoggerFactory.getLogger(EventStoreTest.class);

    private static final String DEFAULT_ORDERDESCRIPTION = "Order Description";
    private static final String DEFAULT_ITEM1 = "ITEM1";
    private static final String DEFAULT_ITEM2 = "ITEM2";

    @Autowired
    EventStoreIfc eventStore;

    @Autowired
    OrderIfc order1,order2,order3;

    @Before
    public void setUp() {
        eventStore.reset();
        List<OrderIfc> orderList = new ArrayList<>(Arrays.asList(order1,order2,order3));
        for (OrderIfc order : orderList) {
            order.setOrderDescription(DEFAULT_ORDERDESCRIPTION);
            order.setItemList(Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2));
            long orderNbr = Long.valueOf(order.getId());
            eventStore.addEvent(orderNbr, order);

        }
    }

    @Test
    public void eventStoreContainsOrder() throws Exception {
        assertEquals(Optional.of(order3),eventStore.getLastOrderEvent(order3.getId()));
    }

    @Test
    public void getOrdersSetTest() {
        assertEquals(3,eventStore.getOrdersSet().get().size());
        log.info(eventStore.toString());
    }

    @Test
    public void resetTest() {
        eventStore.reset();
        assertEquals(0,eventStore.getOrdersSet().get().size());
    }


}