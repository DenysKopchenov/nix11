package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ProductType;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.model.laptop.CPU;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.model.phone.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.service.*;

import java.util.Map;
import java.util.function.Function;

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

    public static Product mapProduct(Map<String, Object> fields) {
        Function<Map<String, Object>, Product> mapToProduct = map -> {
            Object productType = map.get("productType");
            if (productType instanceof ProductType type) {
                return switch (type) {
                    case PHONE -> new Phone(map.getOrDefault("title", "Default").toString(),
                            (Integer) map.getOrDefault("count", 0),
                            (Long) map.getOrDefault("price", 0L),
                            map.getOrDefault("model", "Default").toString(),
                            Manufacturer.valueOf(map.getOrDefault("manufacturer", Manufacturer.NOKIA).toString()));
                    case LAPTOP -> new Laptop.Builder((Long) map.getOrDefault("price", 0L),
                            CPU.valueOf(map.getOrDefault("cpu", CPU.AMD).toString()))
                            .setTittle(map.getOrDefault("title", "Default").toString())
                            .setCount((Integer) map.getOrDefault("count", 0))
                            .build();
                    case BALL -> new Ball(map.getOrDefault("title", "Default").toString(),
                            (Integer) map.getOrDefault("count", 0),
                            (Long) map.getOrDefault("price", 0L),
                            Size.valueOf(map.getOrDefault("size", Size.NONE).toString()));
                };
            } else {
                throw new IllegalArgumentException();
            }
        };
        return mapToProduct.apply(fields);
    }
}
