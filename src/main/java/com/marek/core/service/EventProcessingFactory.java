package com.marek.core.service;

import com.marek.fulfillment.service.FulfillmentCreate;
import com.marek.inventory.service.InventoryCheck;
import com.marek.order.domain.OrderStatus;
import com.marek.shipment.service.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.marek.order.domain.OrderStatus.*;

/**
 * Created by marek.papis on 2016-04-07.
 */
@Service
public class EventProcessingFactory {

    private static final EnumMap<OrderStatus, EventProcessingIfc> map = new EnumMap<>(OrderStatus.class);

    @Autowired
    EventProcessingFactory(FulfillmentCreate fulfillment,
                           InventoryCheck inventoryCheck,
                           Shipment shipment) {
        // [Factory Pattern] example
        map.put(INSERTED_END, inventoryCheck);
        map.put(INVENTORY_END, fulfillment);
        map.put(FULFILLMENT_END, shipment);

    }

    public EventProcessingIfc createEventProcessing(OrderStatus statusEnum) throws IllegalArgumentException {
        //[orElseThrow]
        Optional<EventProcessingIfc> event = Optional.ofNullable(map.get(statusEnum));
        event.orElseThrow(() -> new IllegalArgumentException("Cannot map such eventProcessing instance with name:" + statusEnum.toString()));
        return event.get();
    }

    public List<OrderStatus> getListOfEvents() {
        return map.entrySet()
                .stream()
                .map(e -> e.getKey())
                .collect(Collectors.toList());
    }


}
