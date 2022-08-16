package org.alevel.model.product.television;

import lombok.*;
import org.alevel.model.product.Product;
import org.alevel.model.product.ProductType;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Television extends Product {

    private String series;
    private String diagonal;
    private String screenType;
    private String country;

    public Television(ProductType productType) {
        super(productType);
    }
}
