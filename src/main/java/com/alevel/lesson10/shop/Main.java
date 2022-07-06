package com.alevel.lesson10.shop;

import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.service.BallService;
import com.alevel.lesson10.shop.service.LaptopService;
import com.alevel.lesson10.shop.service.PhoneService;

import java.util.List;
import java.util.Random;

public class Main {

    private static final Random RANDOM = new Random();
    private static final BallService BALL_SERVICE = new BallService();
    private static final LaptopService LAPTOP_SERVICE = new LaptopService();
    private static final PhoneService PHONE_SERVICE = new PhoneService();


    public static void main(String[] args) {
        createAllProducts();
        printAllProducts();

        System.out.println("Updating");
        List<Ball> balls = BALL_SERVICE.findAll();
        Ball updatingBall = balls.get(RANDOM.nextInt(balls.size()));
        updatingBall.setTitle("Updated!!!!!");
        BALL_SERVICE.update(updatingBall);
        BALL_SERVICE.printAll();

        System.out.println("Deleting");
        List<Laptop> laptops = LAPTOP_SERVICE.findAll();
        Laptop deletingLaptop = laptops.get(RANDOM.nextInt(laptops.size()));
        LAPTOP_SERVICE.delete(deletingLaptop.getId());
        LAPTOP_SERVICE.printAll();

        System.out.println("All products");
        printAllProducts();
    }

    private static void createAllProducts() {
        BALL_SERVICE.fillBallRepository();
        LAPTOP_SERVICE.fillLaptopRepository();
        PHONE_SERVICE.fillPhoneRepository();
    }

    private static void printAllProducts() {
        BALL_SERVICE.printAll();
        LAPTOP_SERVICE.printAll();
        PHONE_SERVICE.printAll();
    }
}