package com.marek.order.domain;

import com.marek.Application;
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

import static org.junit.Assert.*;

/**
 * Created by marek.papis on 2016-03-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class OrderTest {

    private static final Logger log = LoggerFactory.getLogger(OrderTest.class);

    private static final String DEFAULT_ORDERDESCRIPTION = "Updated Order Description";
    private static final String UPDATED_ORDERDESCRIPTION = "Updated Order Description";
    private static final String DEFAULT_ITEM1 = "ITEM1";
    private static final String DEFAULT_ITEM2 = "ITEM2";
    private static final int DEFAULT_ITEM_LIST_SIZE = 2;
    private static final OrderStatusEnum DEFAULT_ORDER_STATUS = OrderStatusEnum.INSERTED_END;
    private static final OrderStatusEnum UPDATED_ORDER_STATUS = OrderStatusEnum.FULFILLMENT_START;


    @Autowired
    OrderIfc order;

    @Autowired
    OrderIfc order1;


    @Before
    public void setUp() {
        order.setOrderDescription(DEFAULT_ORDERDESCRIPTION);
        order.setItemList(Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2));
        order.setOrderStatus(DEFAULT_ORDER_STATUS);
    }

    @Test
    public void testGetId() throws Exception {
        log.info("order getid"+order.getId());
        assertTrue(order.getId() > 0);
    }

    @Test
    public void testGetOrderDescription() throws Exception {
        assertEquals(DEFAULT_ORDERDESCRIPTION,order.getOrderDescription());
    }

    @Test
    public void testSetOrderDescription() throws Exception {
        order.setOrderDescription(UPDATED_ORDERDESCRIPTION);
        assertEquals(UPDATED_ORDERDESCRIPTION, order.getOrderDescription());
    }

    @Test
    public void testGetItemList() throws Exception {
        assertEquals(DEFAULT_ITEM_LIST_SIZE,order.getItemList().size());
    }

    @Test
    public void testGetOrderSequence() throws Exception {
        assertTrue(order.getOrderSequence() > 1);
    }

    @Test
    public void testGetOrderStatus() throws Exception {
        assertEquals(DEFAULT_ORDER_STATUS,order.getOrderStatus());
    }

    @Test
    public void testSetOrderStatus() throws Exception {
        order.setOrderStatus(UPDATED_ORDER_STATUS);
        assertEquals(UPDATED_ORDER_STATUS,order.getOrderStatus());
    }

    @Test
    public void testFilterOrderList() throws Exception {
        //usage of Predicate object in Lambda
        List<OrderIfc> orderList = new ArrayList<>(Arrays.asList(order,order1));
        //only 1 order has items list
        List<OrderIfc> orderListWithItems = order.filterOrderList(orderList,(o)-> o.getItemList().size()>0);

        assertEquals(1,orderListWithItems.size());
    }

}