package com.marek.order.web.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marek.Application;
import com.marek.order.domain.Order;
import com.marek.order.domain.OrderStatus;
import com.marek.order.repository.OrderRepository;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.condition.AnyOf.anyOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by marek.papis on 2016-05-27.
 */
@RunWith(SpringJUnit4ClassRunner.class) //application context is created.
@SpringApplicationConfiguration(classes = Application.class)
//specify which application context(s) that should be used in the test.
@WebIntegrationTest // WebApplicationContext should be loaded for the test., embedded web server should be started.
//@WebAppConfiguration // for mocking mvc
public class OrderControllerTest {

    public static final String ORDER_DESCRIPTION = "order descr";
    private static final Logger log = LoggerFactory.getLogger(OrderControllerTest.class);
    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    private MockMvc mock;

    public static byte[] convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        return mapper.writeValueAsBytes(object);
    }

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        //simulates the whole web application context
        //we could also use MockMvcBuilders.standaloneSetup to narrow down the controllers to be setup
        //to the ones only that we need to test
        mock = MockMvcBuilders.webAppContextSetup(webContext).build();

        //new Order
        order = new Order();
        //order.setId(1L);
        order.setOrderDescription(ORDER_DESCRIPTION);

    }

    @Test
    public void addOrder() throws Exception {
        //post an object
        mock.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(order)))
                .andDo(print())
                .andExpect(status().isCreated());

        // put record to DB
        //orderEntityRepository.saveAndFlush(order);

        //Test in the DB if record exists
        List<Order> orders = orderRepository.findAll();
        log.info(":marek:" + orders.size());
        List<Order> order = orders
                .stream()
                .peek(System.out::println)
                .collect(Collectors.toList());

        assertThat(order).hasSize(1);
        assertThat(order.stream().map(o->o.getOrderStatus()).filter(o-> o == OrderStatus.INVENTORY_START || o == OrderStatus.INSERTED_END)).hasSize(1);

    }

    @Test
    public void getOrders() throws Exception {
        mock.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }

}