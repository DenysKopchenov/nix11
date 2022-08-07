package org.alevel.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {
    TELEPHONE("Telephone"),
    TELEVISION("Television");

    private final String name;
}
