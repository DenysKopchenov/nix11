package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.context.ApplicationContext;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.model.phone.Phone;

import java.util.Map;

public final class ServiceContainer {
    private static final AbstractProductService<Ball> ballService;
    private static final AbstractProductService<Phone> phoneService;
    private static final AbstractProductService<Laptop> laptopService;

    private ServiceContainer() {
    }

    static {
        ApplicationContext applicationContext = new ApplicationContext();
        Map<Class<?>, Object> cached = applicationContext.cacheSingletons();
        ballService = (AbstractProductService<Ball>) cached.get(BallService.class);
        phoneService = (AbstractProductService<Phone>) cached.get(PhoneService.class);
        laptopService = (AbstractProductService<Laptop>) cached.get(LaptopService.class);
    }

    public static AbstractProductService<Ball> getBallService() {
        return ballService;
    }

    public static AbstractProductService<Laptop> getLaptopService() {
        return laptopService;
    }

    public static AbstractProductService<Phone> getPhoneService() {
        return phoneService;
    }
}
