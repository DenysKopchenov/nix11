package com.alevel.lesson10.shop.command.primenumber;

import lombok.Getter;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
public class PrimeNumber extends Thread {

    private final List<Integer> list;
    private Long result;
    private final AtomicBoolean working;

    public PrimeNumber(List<Integer> list) {
        this.list = list;
        working = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        countPrimeNumbersInList();
        working.set(false);
    }

    private void countPrimeNumbersInList() {
        long count = list.stream()
                .mapToInt(num -> num)
                .filter(this::isPrimeNumber)
                .count();
        result = count;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    private boolean isPrimeNumber(int number) {
        if (number < 2) {
            return false;
        }
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
