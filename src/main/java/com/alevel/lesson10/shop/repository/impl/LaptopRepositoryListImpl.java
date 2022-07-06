package com.alevel.lesson10.shop.repository.impl;

import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.repository.LaptopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LaptopRepositoryListImpl implements LaptopRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LaptopRepositoryListImpl.class);
    private final List<Laptop> laptops;

    public LaptopRepositoryListImpl() {
        laptops = new ArrayList<>();
    }

    @Override
    public void save(Laptop product) {
        laptops.add(product);
        LOGGER.info("Laptop {} saved", product.getId());
    }

    @Override
    public void saveAll(List<Laptop> products) {
        laptops.addAll(products);
    }

    @Override
    public List<Laptop> findAll() {
        return List.copyOf(laptops);
    }

    @Override
    public Optional<Laptop> findById(String id) {
        return laptops.stream()
                .filter(laptop -> laptop.getId().equals(id))
                .findFirst();
    }

    @Override
    public void update(Laptop product) {
        laptops.stream()
                .filter(laptop -> laptop.getId().equals(product.getId()))
                .findFirst().ifPresent(laptop -> {
                    laptop.setTitle(product.getTitle());
                    laptop.setCount(product.getCount());
                    laptop.setPrice(product.getPrice());
                    laptop.setCpu(product.getCpu());
                });
    }

    @Override
    public void delete(String id) {
        if (laptops.removeIf(laptop -> laptop.getId().equals(id))) {
            LOGGER.info("Laptop {} deleted", id);
        }
    }
}
