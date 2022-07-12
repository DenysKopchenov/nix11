package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.phone.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductContainerTest {

    private ProductContainer<Phone> productContainer;
    private Phone product;

    @BeforeEach
    void setUp() {
        productContainer = new ProductContainer<>();
        product = new Phone("Title", 1, 100, "Model", Manufacturer.APPLE);
        productContainer.setProduct(product);
    }

    @Test
    void getProduct() {
        Assertions.assertEquals(product.getId(), productContainer.getProduct().getId());
    }

    @Test
    void getRandomDiscountAndApply() {
        productContainer.getRandomDiscountAndApply();
        long actualPrice = productContainer.getProduct().getPrice();

        Assertions.assertTrue(actualPrice <= 90 && actualPrice >= 70);
    }

    @Test
    void increaseProductCount() {
        productContainer.increaseProductCount(10);

        Assertions.assertEquals(11, product.getCount());
    }
}