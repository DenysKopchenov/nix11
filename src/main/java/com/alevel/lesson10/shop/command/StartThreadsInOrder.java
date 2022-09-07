package com.alevel.lesson10.shop.command;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class StartThreadsInOrder implements Command {

    @SneakyThrows
    @Override
    public void execute() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Runnable runnable = () -> System.out.println("Hello from " + Thread.currentThread().getName());
            threads.add(new Thread(runnable));
        }

        for (int i = threads.size() - 1; i >= 0; i--) {
            Thread thread = threads.get(i);
            thread.start();
            thread.join();
        }
    }
}
