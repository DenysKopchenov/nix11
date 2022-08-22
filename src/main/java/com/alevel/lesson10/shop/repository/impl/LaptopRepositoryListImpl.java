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
        if (product != null) {
            laptops.add(product);
            LOGGER.info("Laptop {} saved", product.getId());
        } else {
            throw new IllegalArgumentException("Laptop can not be null");
        }
    }

    @Override
    public void saveAll(List<Laptop> products) {
        if (products != null) {
            laptops.addAll(products);
        } else {
            throw new IllegalArgumentException("List can not be null");
        }
    }

    @Override
    public List<Laptop> findAll() {
        return laptops;
    }

    @Override
    public Optional<Laptop> findById(String id) {
        return laptops.stream()
                .filter(laptop -> laptop.getId().equals(id))
                .findFirst();
    }

    @Override
    public void update(Laptop product) {
        Optional<Laptop> optionalLaptop = findById(product.getId());
        if (optionalLaptop.isPresent()) {
            Laptop laptop = optionalLaptop.get();
            laptop.setTitle(product.getTitle());
            laptop.setCount(product.getCount());
            laptop.setPrice(product.getPrice());
        } else {
            throw new IllegalArgumentException("No laptop found");
        }
    }

    @Override
    public void delete(String id) {
        if (laptops.removeIf(laptop -> laptop.getId().equals(id))) {
            LOGGER.info("Laptop {} deleted", id);
        }
    }
}
