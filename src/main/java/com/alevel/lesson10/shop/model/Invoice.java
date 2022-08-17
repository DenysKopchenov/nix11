package com.alevel.lesson10.shop.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Invoice {

    private String id;
    private double sum;
    private List<Product> products;
    private LocalDateTime time;

    public Invoice() {
        id = UUID.randomUUID().toString();
    }
}
