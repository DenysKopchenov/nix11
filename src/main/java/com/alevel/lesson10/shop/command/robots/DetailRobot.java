package com.alevel.lesson10.shop.command.robots;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class DetailRobot implements Runnable {
    private Factory instance = Factory.getInstance();
    private AtomicInteger detailCreatingProcess = instance.getDetailCreatingProcess();
    private static final int LOWER_BOUND = 10;
    private static final int UPPER_BOUND = 21;
    private static final int SLEEP = 2000;

    @SneakyThrows
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            while (detailCreatingProcess.get() < 100) {
                int creatingPercent = random.nextInt(LOWER_BOUND, UPPER_BOUND);
                System.out.println("Robot creating detail percent = " + creatingPercent);
                int currentCreatingProcess = detailCreatingProcess.addAndGet(creatingPercent);
                System.out.println("Current creating detail percent = " + currentCreatingProcess);
                Thread.sleep(SLEEP);
            }
        }
    }
}
