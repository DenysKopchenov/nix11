package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.Product;

import java.util.Random;

public class ProductContainer<T extends Product> {

    private T product;
    private static final Random RANDOM = new Random();

    public T getProduct() {
        return product;
    }

    public void setProduct(T product) {
        this.product = product;
    }

    public void getRandomDiscountAndApply() {
        int lowerBoundPercent = 10;
        int upperBoundPercent = 31;
        int discount = RANDOM.nextInt(lowerBoundPercent, upperBoundPercent);
        long actualPrice = product.getPrice();
        long discountPrice = actualPrice - (actualPrice * discount / 100);
        this.product.setPrice(discountPrice);
    }

    public <X extends Number> void increaseProductCount(X count) {
        product.setCount(product.getCount() + count.intValue());
    }
}
