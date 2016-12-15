package com.marek.fulfillment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marek.order.domain.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by marek.papis on 2016-06-17.
 */
@Entity
@Table(name = "fulfillment")
public class Fulfillment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(max = 20)
    private String description;

    @OneToMany(mappedBy = "fulfillment")
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();

    @Override
    public String toString() {
        return "Fulfillment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", orders=" + orders +
                '}';
    }

    public long getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
