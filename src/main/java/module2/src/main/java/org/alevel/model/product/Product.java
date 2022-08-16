package org.alevel.model.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class Product {

    private String id;
    private long price;
    private ProductType productType;

    protected Product(ProductType productType) {
        id = UUID.randomUUID().toString();
        this.productType = productType;
    }
}
