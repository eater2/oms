package com.marek.core.service;

/**
 * Created by marek.papis on 2016-03-23.
 */
public interface DelegatorIfc {

    public int delegate();
    public void setEventStore(EventStoreIfc eventStore);
}
