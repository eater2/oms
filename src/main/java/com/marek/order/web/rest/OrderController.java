package com.marek.order.web.rest;

import com.marek.order.domain.Order;
import com.marek.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by marek.papis on 2016-05-26.
 */
@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RequestMapping(value = "/orders",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Order> addOrder(@RequestBody @Valid Order order) throws URISyntaxException {
        if (order.getOrderDescription() == null) {
            return ResponseEntity.badRequest().header("Failure", "A new order must have a description").body(null);
        }
        Order result = orderRepository.saveAndFlush(order);
        return ResponseEntity.created(new URI("/api/orders/"))
                .body(result);
    }

    @RequestMapping(value = "/orders",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Order> getOrders() throws URISyntaxException {
        return  orderRepository.findAll();
    }
}
