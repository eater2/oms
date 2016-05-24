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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by marek.papis on 2016-03-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class OrderTest {

    private static final Logger log = LoggerFactory.getLogger(OrderTest.class);

    private static final long DEFAULT_ID = 10;
    private static final String DEFAULT_ORDERDESCRIPTION = "Updated Order Description";
    private static final String DEFAULT_ITEM1 = "ITEM1";
    private static final String DEFAULT_ITEM2 = "ITEM2";
    private static final int DEFAULT_ITEM_LIST_SIZE = 2;
    private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.INSERTED_END;
    private static final OrderStatus UPDATED_ORDER_STATUS = OrderStatus.FULFILLMENT_START;


    @Autowired
    Order order;

    @Autowired
    Order order1;

    @Autowired
    Order order2;

    @Before
    public void setUp() {
        order = order.copyFrom(DEFAULT_ID, DEFAULT_ORDER_STATUS, DEFAULT_ORDERDESCRIPTION, Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2));
    }

    @Test
    public void testGetId() throws Exception {
        assertThat(DEFAULT_ID).isEqualTo(order.getId());
    }

    @Test
    public void testGetOrderDescription() throws Exception {
        assertThat(DEFAULT_ORDERDESCRIPTION).isEqualTo(order.getOrderDescription());
    }

    @Test
    public void testGetItemList() throws Exception {
        assertThat(DEFAULT_ITEM_LIST_SIZE).isEqualTo(order.getItemList().size());
    }

    @Test
    public void testGetOrderSequence() throws Exception {
        assertThat(order.getOrderSequence()).isGreaterThan(1);
    }

    @Test
    public void testGetOrderStatus() throws Exception {
        assertThat(DEFAULT_ORDER_STATUS).isEqualTo(order.getOrderStatus());
    }

    @Test
    public void testFilterOrderList() throws Exception {
        List<Order> orderList = new ArrayList<>(Arrays.asList(order, order1));
        //only 1 order has items list not empty
        //[java8] [Predicate] usage of Predicate object in Lambda
        List<Order> orderListWithItems = order.filterOrderList(orderList, (o) -> o.getItemList().size() > 0);
        assertThat(1).isEqualTo(orderListWithItems.size());
    }

    @Test
    public void testCopyFrom2Arguments() throws Exception {
        order2 = order.copyFrom(order, OrderStatus.FULFILLMENT_END);
        assertThat(OrderStatus.FULFILLMENT_END).isEqualTo(order2.getOrderStatus());
        assertThat(order).isNotEqualTo(order2);
    }


}