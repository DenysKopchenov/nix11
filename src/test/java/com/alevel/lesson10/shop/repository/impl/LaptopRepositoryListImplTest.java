package com.alevel.lesson10.shop.repository.impl;

import com.alevel.lesson10.shop.model.laptop.CPU;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.repository.LaptopRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class LaptopRepositoryListImplTest {

    private LaptopRepository target;
    private Laptop laptop;
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        target = new LaptopRepositoryListImpl();
        laptop = new Laptop("Title - " + random.nextInt(),
                random.nextInt(),
                random.nextLong(),
                getRandomCPU());
    }

    private CPU getRandomCPU() {
        CPU[] values = CPU.values();
        int index = random.nextInt(values.length);
        return values[index];
    }

    @Test
    void save() {
        target.save(laptop);
        List<Laptop> laptops = target.findAll();
        Laptop actualLaptop = laptops.get(0);
        Assertions.assertEquals(1, laptops.size());
        Assertions.assertEquals(laptop.getId(), actualLaptop.getId());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void saveAll_singleLaptop() {
        target.saveAll(List.of(laptop));
        List<Laptop> laptops = target.findAll();
        Assertions.assertEquals(1, laptops.size());
    }

    @Test
    void saveAll_manyLaptops() {
        target.saveAll(List.of(laptop, laptop));
        List<Laptop> laptops = target.findAll();
        Assertions.assertEquals(2, laptops.size());
    }

    @Test
    void saveAll_manyLaptops_hasNull() {
        List<Laptop> laptops = new ArrayList<>();
        laptops.add(laptop);
        laptops.add(null);
        target.saveAll(laptops);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void saveAll_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(null));
    }

    @Test
    void saveAll_empty() {
        target.saveAll(Collections.emptyList());
        List<Laptop> laptops = target.findAll();
        Assertions.assertEquals(0, laptops.size());
    }

    @Test
    void findAll() {
        target.saveAll(List.of(laptop, laptop));
        List<Laptop> laptops = target.findAll();
        Assertions.assertEquals(2, laptops.size());
    }

    @Test
    void findById() {
        target.save(laptop);
        Optional<Laptop> actualLaptop = target.findById(laptop.getId());
        Assertions.assertTrue(actualLaptop.isPresent());
    }

    @Test
    void findById_wrongId() {
        target.save(laptop);
        Optional<Laptop> actualLaptop = target.findById("123");
        Assertions.assertTrue(actualLaptop.isEmpty());
    }

    @Test
    void update() {
        String newTitle = "New Title";
        target.save(laptop);
        laptop.setTitle(newTitle);
        target.update(laptop);

        List<Laptop> laptops = target.findAll();

        Assertions.assertEquals(newTitle, laptops.get(0).getTitle());
        Assertions.assertEquals(laptop.getId(), laptops.get(0).getId());
        Assertions.assertEquals(laptop.getCpu(), laptops.get(0).getCpu());
    }

    @Test
    void update_noLaptop() {
        target.save(laptop);

        Assertions.assertThrows(IllegalArgumentException.class, () -> target.update(new Laptop("Title", 1, 1, CPU.APPLE)));

        List<Laptop> laptops = target.findAll();
        Assertions.assertEquals(1, laptops.size());
        Assertions.assertEquals(laptop.getId(), laptops.get(0).getId());
    }

    @Test
    void delete() {
        target.save(laptop);
        target.delete(laptop.getId());

        List<Laptop> laptops = target.findAll();

        Assertions.assertTrue(laptops.isEmpty());
    }

    @Test
    void delete_noLaptop() {
        target.save(laptop);
        target.delete("123");

        List<Laptop> laptops = target.findAll();

        Assertions.assertEquals(1, laptops.size());
    }
}