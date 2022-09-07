package com.alevel.lesson10.shop.command.primenumber;

import com.alevel.lesson10.shop.command.Command;
import lombok.SneakyThrows;

import java.util.List;
import java.util.stream.IntStream;

public class PrimeNumberCounter implements Command {


    @SneakyThrows
    @Override
    public void execute() {
        List<Integer> firstRange = IntStream.range(1, 500)
                .boxed()
                .toList();
        List<Integer> secondRange = IntStream.range(500, 1000)
                .boxed()
                .toList();

        PrimeNumber thread1 = new PrimeNumber(firstRange);
        PrimeNumber thread2 = new PrimeNumber(secondRange);

        thread1.start();
        thread2.start();

        while (thread1.getWorking().get() || thread2.getWorking().get()) ;

        long result = thread1.getResult() + thread2.getResult();
        System.out.println(Thread.currentThread().getName() + " Total prime numbers count = " + result);
    }
}
