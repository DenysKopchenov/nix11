package com.alevel.lesson10.shop.command;

import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.service.*;

import java.util.ArrayList;
import java.util.List;

public class Print implements Command {

    private static final AbstractProductService<Ball> BALL_SERVICE = ServiceContainer.getBallService();
    private static final AbstractProductService<Phone> PHONE_SERVICE = ServiceContainer.getPhoneService();
    private static final AbstractProductService<Laptop> LAPTOP_SERVICE = ServiceContainer.getLaptopService();

    @Override
    public void execute() {
        List<Product> allProducts = new ArrayList<>();
        allProducts.addAll(BALL_SERVICE.findAll());
        allProducts.addAll(PHONE_SERVICE.findAll());
        allProducts.addAll(LAPTOP_SERVICE.findAll());

        if (allProducts.isEmpty()) {
            System.out.println("0 products was found");
        }
        allProducts.forEach(System.out::println);
    }
}
