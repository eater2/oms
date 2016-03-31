package com.marek.core.service;

import com.marek.Application;
import com.marek.order.domain.OrderIfc;
import com.marek.order.domain.OrderStatusEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;
import static org.junit.Assert.*;

/**
 * Created by marek.papis on 2016-03-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DelegatorTest {

    private static final Logger log = LoggerFactory.getLogger(DelegatorTest.class);

    private static final long DEFAULT_ID = 10;
    private static final String DEFAULT_ORDERDESCRIPTION = "Order Description";
    private static final OrderStatusEnum DEFAULT_ORDER_STATUS = OrderStatusEnum.INSERTED_END;
    private static final String DEFAULT_ITEM1 = "ITEM1";
    private static final String DEFAULT_ITEM2 = "ITEM2";

    @Autowired
    EventStoreIfc eventStore;

    @Autowired
    DelegatorIfc delegator;

    @Autowired
    OrderIfc order1;

    @Autowired
    OrderIfc order2;

    @Autowired
    OrderIfc order3;

    @Before
    public void setUp() throws Exception {
        order1.copyFrom(DEFAULT_ID+2,DEFAULT_ORDER_STATUS,DEFAULT_ORDERDESCRIPTION,Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2));
        order2.copyFrom(DEFAULT_ID,DEFAULT_ORDER_STATUS,DEFAULT_ORDERDESCRIPTION,Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2));
        order3.copyFrom(DEFAULT_ID+1,DEFAULT_ORDER_STATUS,DEFAULT_ORDERDESCRIPTION,Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2));

        List<OrderIfc> orderList = new ArrayList<>(Arrays.asList(order1,order2,order3));

        //usage of [java8] [Comparator] object to sort Order elements without specifying copmareTo in Order Class
        //1st option
        orderList.sort((o1, o2) -> Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId())));
        //2nd option - the same exactly
        orderList.sort(comparing(OrderIfc::getId));

        //usage of [java8] [Predicate] object in Lambda
        List<OrderIfc> orderListWithItems = order1.filterOrderList(orderList,(o)-> o.getItemList().size()>0);

        //Push 3 orders into EventStore
        orderList.stream().forEach(o->eventStore.addEvent(o.getId(),o));;

    }

    @Test
    public void delegateTest() throws Exception {
        log.info("event store size:"+ eventStore.getOrdersSet().size());
        assertEquals(3,delegator.delegate());
        assertEquals(OrderStatusEnum.FULFILLMENT_END,eventStore.getLastOrderEvent(order1.getId()).get().getOrderStatus());
        assertEquals(OrderStatusEnum.FULFILLMENT_END,eventStore.getLastOrderEvent(order2.getId()).get().getOrderStatus());
        assertEquals(OrderStatusEnum.FULFILLMENT_END,eventStore.getLastOrderEvent(order3.getId()).get().getOrderStatus());
        log.info(eventStore.toString());
    }
}