package com.alevel.lesson10.shop.command.robots;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SchemaRobot implements Runnable {
    private Factory instance = Factory.getInstance();
    private AtomicInteger detailCreatingProcess = instance.getDetailCreatingProcess();
    private AtomicInteger programmingMicroschemaProcess = instance.getProgrammingMicroschemaProcess();
    private static final int LOWER_BOUND = 25;
    private static final int UPPER_BOUND = 36;
    private static final int SLEEP = 1000;
    private static final int FAIL_PERCENT = 30;

    @SneakyThrows
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            if (programmingMicroschemaProcess.get() == 0 && detailCreatingProcess.get() >= 100) {
                System.out.println("Robot 4 starts");
            }
            while (programmingMicroschemaProcess.get() < 100 && detailCreatingProcess.get() >= 100) {
                int programmingPercent = random.nextInt(LOWER_BOUND, UPPER_BOUND);
                System.out.println("Robot 4 " + programmingPercent + " percents of process");
                int failChance = random.nextInt(1, 101);
                if (failChance <= FAIL_PERCENT) {
                    System.out.println("FAIL");
                    programmingMicroschemaProcess.set(0);
                } else {
                    programmingMicroschemaProcess.getAndAdd(programmingPercent);
                }
                System.out.println("Robot 4 process percent = " + programmingMicroschemaProcess.get());
                Thread.sleep(SLEEP);
            }
        }
    }
}
