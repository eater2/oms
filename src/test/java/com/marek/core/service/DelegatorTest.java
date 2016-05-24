package com.marek.core.service;

import com.marek.Application;
import com.marek.order.domain.Order;
import com.marek.order.domain.OrderStatus;
import com.marek.utils.howto.DesignPatterns;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.marek.order.domain.OrderStatus.INSERTED_END;
import static com.marek.order.domain.OrderStatus.SHIP_END;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by marek.papis on 2016-03-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DelegatorTest {

    private static final Logger log = LoggerFactory.getLogger(DelegatorTest.class);

    private static final long DEFAULT_ID = 10;
    private static final String DEFAULT_ORDERDESCRIPTION = "Order Description";
    private static final OrderStatus DEFAULT_ORDER_STATUS = INSERTED_END;
    private static final String DEFAULT_ITEM1 = "ITEM1";
    private static final String DEFAULT_ITEM2 = "ITEM2";

    @Autowired
    EventStore eventStore;

    @Autowired
    Delegator delegator;

    @Autowired
    Order order;

    Order order1, order2, order3;

    @Before
    public void setUp() throws Exception {
        order1 = order.copyFrom(DEFAULT_ID + 2, DEFAULT_ORDER_STATUS, DEFAULT_ORDERDESCRIPTION, Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2));
        order2 = order.copyFrom(DEFAULT_ID, DEFAULT_ORDER_STATUS, DEFAULT_ORDERDESCRIPTION, Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2));
        order3 = order.copyFrom(DEFAULT_ID + 1, DEFAULT_ORDER_STATUS, DEFAULT_ORDERDESCRIPTION, Arrays.asList(DEFAULT_ITEM1, DEFAULT_ITEM2));

        List<Order> orderList = new ArrayList<>(Arrays.asList(order1, order2, order3));

        //usage of [java8] [Comparator] object to sort Order elements without specifying copmareTo in Order Class
        //1st option
        orderList.sort((o1, o2) -> Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId())));
        //2nd option - the same exactly
        orderList.sort(comparing(Order::getId));

        //usage of [java8] [Predicate] object in Lambda
        List<Order> orderListWithItems = order1.filterOrderList(orderList, (o) -> o.getItemList().size() > 0);

        //Push 3 orders into EventStore
        orderList.stream().forEach(o -> eventStore.addEvent(o.getId(), o));
        ;
    }

    @Test
    public void shouldCompleteSpecificOrder() throws Exception {
        ///[Reflection] mechanism to test private methods
        Method method = Delegator.class.getDeclaredMethod("produceEvent", Long.class, OrderStatus.class, Order.class);
        method.setAccessible(true);
        CompletableFuture<Order> future = (CompletableFuture<Order>) method.invoke(delegator, order1.getId(), order1.getOrderStatus(), order1);

        assertThat(order1.getId()).isEqualTo(future.get().getId());
    }

    @Test
    public void shouldCompleteAllOrders() throws Exception {
        //3 iteratons to process all order statuses : inserted -> inventoryCheck -> fulfillmnent -> orderComplete

        delegator.delegate().get();
        delegator.delegate().get();
        delegator.delegate().get();

        //dump whole event Store
        log.info(eventStore.toString());

        assertThat(SHIP_END).isEqualTo(eventStore.getLastOrderEvent(order1.getId()).get().getOrderStatus());
        assertThat(SHIP_END).isEqualTo(eventStore.getLastOrderEvent(order2.getId()).get().getOrderStatus());
        assertThat(SHIP_END).isEqualTo(eventStore.getLastOrderEvent(order3.getId()).get().getOrderStatus());

        DesignPatterns.whenIsTheAdapterDesignPatternUsed();
        com.marek.utils.howto.DesignPatterns.whatIsTheAdapterDesignPattern();

    }
}