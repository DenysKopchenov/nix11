package com.alevel.lesson10.shop.command.robots;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Factory {

    private AtomicInteger fuel = new AtomicInteger(0);
    private AtomicInteger detailCreatingProcess = new AtomicInteger(0);
    private AtomicInteger programmingMicroschemaProcess = new AtomicInteger(0);
    private AtomicBoolean completed = new AtomicBoolean(false);

    private static Factory instance;

    private Factory() {
    }

    public static Factory getInstance() {
        if (instance == null) {
            synchronized (Factory.class) {
                if (instance == null) {
                    instance = new Factory();
                }
            }
        }
        return instance;
    }


    public AtomicInteger getFuel() {
        return fuel;
    }

    public AtomicInteger getDetailCreatingProcess() {
        return detailCreatingProcess;
    }

    public AtomicInteger getProgrammingMicroschemaProcess() {
        return programmingMicroschemaProcess;
    }

    public AtomicBoolean getCompleted() {
        return completed;
    }
}
