package com.alevel.lesson10.shop;

import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ProductComparator;
import com.alevel.lesson10.shop.repository.impl.BallRepositoryListImpl;
import com.alevel.lesson10.shop.repository.impl.LaptopRepositoryListImpl;
import com.alevel.lesson10.shop.repository.impl.PhoneRepositoryListImpl;
import com.alevel.lesson10.shop.service.BallService;
import com.alevel.lesson10.shop.service.LaptopService;
import com.alevel.lesson10.shop.service.PhoneService;

import java.util.Set;
import java.util.TreeSet;

public class Main {
    private static final BallService BALL_SERVICE = new BallService(new BallRepositoryListImpl());
    private static final LaptopService LAPTOP_SERVICE = new LaptopService(new LaptopRepositoryListImpl());
    private static final PhoneService PHONE_SERVICE = new PhoneService(new PhoneRepositoryListImpl());


    public static void main(String[] args) {
        BALL_SERVICE.createAndFillRepository(15);
        Set<Product> ballSet = new TreeSet<>(new ProductComparator<>());
        ballSet.addAll(BALL_SERVICE.findAll());
        ballSet.forEach(System.out::println);
    }

    private static void createAllProducts() {
        BALL_SERVICE.createAndFillRepository(5);
        LAPTOP_SERVICE.createAndFillRepository(5);
        PHONE_SERVICE.createAndFillRepository(5);
    }

    private static void printAllProducts() {
        BALL_SERVICE.printAll();
        LAPTOP_SERVICE.printAll();
        PHONE_SERVICE.printAll();
    }
}