package com.alevel.lesson10.shop.repository.impl;

import com.alevel.lesson10.shop.model.phone.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.PhoneRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class PhoneRepositoryListImplTest {

    private PhoneRepository target;
    private Phone phone;
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        target = new PhoneRepositoryListImpl();
        phone = new Phone("Title - " + random.nextInt(),
                random.nextInt(),
                random.nextLong(),
                "Model - " + random.nextInt(),
                getRandomManufacturer());
    }

    private Manufacturer getRandomManufacturer() {
        Manufacturer[] values = Manufacturer.values();
        int index = random.nextInt(values.length);
        return values[index];
    }


    @Test
    void save() {
        target.save(phone);
        List<Phone> phones = target.findAll();
        Phone actualPhone = phones.get(0);
        Assertions.assertEquals(1, phones.size());
        Assertions.assertEquals(phone.getId(), actualPhone.getId());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void saveAll_singlePhone() {
        target.saveAll(List.of(phone));
        List<Phone> phones = target.findAll();
        Assertions.assertEquals(1, phones.size());
    }

    @Test
    void saveAll_manyPhones() {
        target.saveAll(List.of(phone, phone));
        List<Phone> phones = target.findAll();
        Assertions.assertEquals(2, phones.size());
    }

    @Test
    void saveAll_manyPhones_hasNull() {
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        phones.add(null);
        target.saveAll(phones);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void saveAll_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(null));
    }

    @Test
    void saveAll_empty() {
        target.saveAll(Collections.emptyList());
        List<Phone> phones = target.findAll();
        Assertions.assertEquals(0, phones.size());
    }

    @Test
    void findAll() {
        target.saveAll(List.of(phone, phone));
        List<Phone> phones = target.findAll();
        Assertions.assertEquals(2, phones.size());
    }

    @Test
    void findById() {
        target.save(phone);
        Optional<Phone> actualPhone = target.findById(phone.getId());
        Assertions.assertTrue(actualPhone.isPresent());
    }

    @Test
    void findById_wrongId() {
        target.save(phone);
        Optional<Phone> actualPhone = target.findById("123");
        Assertions.assertTrue(actualPhone.isEmpty());
    }

    @Test
    void update() {
        String newTitle = "New Title";
        target.save(phone);
        phone.setTitle(newTitle);
        target.update(phone);

        List<Phone> phones = target.findAll();

        Assertions.assertEquals(newTitle, phones.get(0).getTitle());
        Assertions.assertEquals(phone.getId(), phones.get(0).getId());
        Assertions.assertEquals(phone.getModel(), phones.get(0).getModel());
    }

    @Test
    void update_noPhone() {
        target.save(phone);

        Assertions.assertThrows(IllegalArgumentException.class, () -> target.update(new Phone("Title", 1, 1, "Model", Manufacturer.APPLE)));

        List<Phone> phones = target.findAll();
        Assertions.assertEquals(1, phones.size());
        Assertions.assertEquals(phone.getId(), phones.get(0).getId());
    }

    @Test
    void delete() {
        target.save(phone);
        target.delete(phone.getId());

        List<Phone> phones = target.findAll();

        Assertions.assertTrue(phones.isEmpty());
    }

    @Test
    void delete_noPhone() {
        target.save(phone);
        target.delete("123");

        List<Phone> phones = target.findAll();

        Assertions.assertEquals(1, phones.size());
    }
}