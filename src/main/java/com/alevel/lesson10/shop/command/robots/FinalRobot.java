package com.alevel.lesson10.shop.command.robots;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class FinalRobot implements Runnable {
    private Factory instance = Factory.getInstance();
    private AtomicInteger programmingMicroschemaProcess = instance.getProgrammingMicroschemaProcess();
    private AtomicInteger fuel = instance.getFuel();
    private static final int STEP = 10;
    private static final int LOWER_BOUND = 350;
    private static final int UPPER_BOUND = 701;
    private static final int SLEEP = 1000;

    @SneakyThrows
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int process = 0;
            if (programmingMicroschemaProcess.get() >= 100) {
                System.out.println("Robot 5 start");
            }
            while (programmingMicroschemaProcess.get() >= 100 && process < 100) {
                int fuelNeed = random.nextInt(LOWER_BOUND, UPPER_BOUND);
                System.out.println("Robot 5 fuel need = " + fuelNeed);
                System.out.println("Current fuel = " + fuel.get());
                while (fuelNeed > fuel.get()) ;
                fuel.set(fuel.get() - fuelNeed);
                process += STEP;
                System.out.println("Robot5 progress = " + process);
                Thread.sleep(SLEEP);
                if (process == 100) {
                    programmingMicroschemaProcess.set(0);
                    instance.getDetailCreatingProcess().set(0);
                }
            }
        }
    }
}
