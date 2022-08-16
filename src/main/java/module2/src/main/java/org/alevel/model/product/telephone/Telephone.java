package org.alevel.model.product.telephone;

import lombok.*;
import org.alevel.model.product.Product;
import org.alevel.model.product.ProductType;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Telephone extends Product {

    private String series;
    private String model;
    private String screenType;

    public Telephone(ProductType productType) {
        super(productType);
    }
}
