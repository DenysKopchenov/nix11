package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.laptop.CPU;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.repository.LaptopRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LaptopService {

    private static final Random RANDOM = new Random();
    private LaptopRepository laptopRepository;

    public LaptopService(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    public void fillLaptopRepository() {
        for (int i = 0; i < 5; i++) {
            laptopRepository.save(new Laptop("Title - " + RANDOM.nextInt(),
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
        laptopRepository.findAll().forEach(System.out::println);
        System.out.println("=".repeat(20));
    }

    public Laptop findById(String id) {
        Optional<Laptop> optionalLaptop = laptopRepository.findById(id);
        if (optionalLaptop.isPresent()) {
            return optionalLaptop.get();
        }
        throw new RuntimeException();
    }

    public void update(Laptop laptop) {
        laptopRepository.update(laptop);
    }

    public void delete(String id) {
        laptopRepository.delete(id);
    }

    public List<Laptop> findAll() {
        return laptopRepository.findAll();
    }

    public void save(Laptop laptop) {
        laptopRepository.save(laptop);
    }
}
