package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.impl.BallRepositoryListImpl;
import com.alevel.lesson10.shop.repository.impl.LaptopRepositoryListImpl;
import com.alevel.lesson10.shop.repository.impl.PhoneRepositoryListImpl;

public final class ServiceContainer {
    private static final AbstractProductService<Ball> BALL_SERVICE = new BallService(new BallRepositoryListImpl());
    private static final AbstractProductService<Phone> PHONE_SERVICE = new PhoneService(new PhoneRepositoryListImpl());
    private static final AbstractProductService<Laptop> LAPTOP_SERVICE = new LaptopService(new LaptopRepositoryListImpl());

    private ServiceContainer() {
    }

    public static AbstractProductService<Ball> getBallService() {
        return BALL_SERVICE;
    }

    public static AbstractProductService<Laptop> getLaptopService() {
        return LAPTOP_SERVICE;
    }

    public static AbstractProductService<Phone> getPhoneService() {
        return PHONE_SERVICE;
    }
}
