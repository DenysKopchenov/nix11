package com.alevel.lesson10.shop.command.robots;

import com.alevel.lesson10.shop.command.Command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateDetail implements Command {
    @Override
    public void execute() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(new FuelRobot());
        executorService.execute(new DetailRobot());
        executorService.execute(new DetailRobot());
        executorService.execute(new SchemaRobot());
        executorService.execute(new FinalRobot());
    }
}
