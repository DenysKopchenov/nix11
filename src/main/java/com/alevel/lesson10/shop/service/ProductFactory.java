package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.ProductType;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.service.*;

public class ProductFactory {
    private static final AbstractProductService<Ball> BALL_SERVICE = ServiceContainer.getBallService();
    private static final AbstractProductService<Phone> PHONE_SERVICE = ServiceContainer.getPhoneService();
    private static final AbstractProductService<Laptop> LAPTOP_SERVICE = ServiceContainer.getLaptopService();

    private ProductFactory() {

    }

    public static void createAndSave(ProductType productType) {
        switch (productType) {
            case BALL -> BALL_SERVICE.createAndFillRepository(1);
            case PHONE -> PHONE_SERVICE.createAndFillRepository(1);
            case LAPTOP -> LAPTOP_SERVICE.createAndFillRepository(1);
            default -> throw new IllegalArgumentException("Unknown product type");
        }
    }
}
