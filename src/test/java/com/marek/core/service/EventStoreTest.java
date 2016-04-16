package com.marek.core.service;

import com.marek.Application;
import com.marek.order.domain.Order;
import com.marek.order.domain.OrderStatus;
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

    private static final long DEFAULT_ID = 10;
    private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.INSERTED_END;
    private static final String DEFAULT_ORDER_DESCRIPTION = "Order Description";
    private static final String DEFAULT_ITEM1 = "ITEM1";
    private static final String DEFAULT_ITEM2 = "ITEM2";

    @Autowired
    EventStore eventStore;

    @Autowired
    Order order1;

    @Before
    public void setUp() {
        eventStore.reset();
        eventStore.addEvent(DEFAULT_ID,order1.copyFrom(DEFAULT_ID,DEFAULT_ORDER_STATUS, DEFAULT_ORDER_DESCRIPTION,Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2)));
        eventStore.addEvent(DEFAULT_ID+1,order1.copyFrom(DEFAULT_ID+1,DEFAULT_ORDER_STATUS, DEFAULT_ORDER_DESCRIPTION,Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2)));
        eventStore.addEvent(DEFAULT_ID+2,order1.copyFrom(DEFAULT_ID+2,DEFAULT_ORDER_STATUS, DEFAULT_ORDER_DESCRIPTION,Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2)));
    }

    @Test
    public void eventStoreContainsOrder() throws Exception {
        assertEquals(DEFAULT_ID,eventStore.getLastOrderEvent(DEFAULT_ID).get().getId());
    }

    @Test
    public void getOrdersSetTest() {
        assertEquals(3,eventStore.getOrdersSet().size());
        log.info(eventStore.toString());
    }

    @Test
    public void resetTest() {
        eventStore.reset();
        assertEquals(0,eventStore.getOrdersSet().size());
    }


}