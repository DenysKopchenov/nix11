package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.laptop.CPU;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.repository.ProductRepository;

public class LaptopService extends AbstractProductService<Laptop> {

    public LaptopService(ProductRepository<Laptop> repository) {
        super(repository);
    }

    private CPU getRandomCPU() {
        CPU[] values = CPU.values();
        int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    @Override
    protected Laptop createProduct() {
        return new Laptop.Builder(RANDOM.nextLong(), getRandomCPU())
                .setTittle("Title - " + RANDOM.nextInt())
                .setCount(RANDOM.nextInt(1000))
                .build();
    }
}
