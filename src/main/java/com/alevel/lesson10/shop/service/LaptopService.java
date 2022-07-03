package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.laptop.CPU;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.repository.LaptopRepository;
import com.alevel.lesson10.shop.repository.impl.LaptopRepositoryListImpl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LaptopService {

    private static final Random RANDOM = new Random();
    private static final LaptopRepository LAPTOP_REPOSITORY = new LaptopRepositoryListImpl();

    public void fillLaptopRepository() {
        for (int i = 0; i < 5; i++) {
            LAPTOP_REPOSITORY.save(new Laptop("Title - " + RANDOM.nextInt(),
                    RANDOM.nextInt(),
                    RANDOM.nextLong(),
                    getRandomCPU()));
        }
    }

    private CPU getRandomCPU() {
        CPU[] values = CPU.values();
        int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        LAPTOP_REPOSITORY.findAll().forEach(System.out::println);
        System.out.println("=".repeat(20));
    }

    public Laptop findById(String id) {
        Optional<Laptop> optionalLaptop = LAPTOP_REPOSITORY.findById(id);
        if (optionalLaptop.isPresent()) {
            return optionalLaptop.get();
        }
        throw new RuntimeException();
    }

    public void update(Laptop laptop) {
        LAPTOP_REPOSITORY.update(laptop);
    }

    public void delete(String id) {
        LAPTOP_REPOSITORY.delete(id);
    }

    public List<Laptop> findAll() {
        return LAPTOP_REPOSITORY.findAll();
    }

    public void save(Laptop laptop) {
        LAPTOP_REPOSITORY.save(laptop);
    }
}
