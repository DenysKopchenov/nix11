package com.alevel.lesson10.shop.command.robots;

import lombok.SneakyThrows;

import java.util.Random;

public class FuelRobot implements Runnable {

    private static final int LOWER_BOUND = 500;
    private static final int UPPER_BOUND = 1001;
    private static final int SLEEP = 3000;

    @SneakyThrows
    @Override
    public void run() {
        Factory instance = Factory.getInstance();
        while (!instance.getCompleted().get()) {
            int foundFuel = new Random().nextInt(LOWER_BOUND, UPPER_BOUND);
            System.out.println("Robot1 get " + foundFuel + " fuel");
            int currentFuel = instance.getFuel().addAndGet(foundFuel);
            System.out.println("Current fuel = " + currentFuel);
            Thread.sleep(SLEEP);
        }
    }
}
