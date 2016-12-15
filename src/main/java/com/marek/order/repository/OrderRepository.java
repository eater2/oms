package com.marek.order.repository;

import com.marek.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by marek.papis on 2016-05-26.
 */

public interface OrderRepository extends JpaRepository<Order, Long> {

}
