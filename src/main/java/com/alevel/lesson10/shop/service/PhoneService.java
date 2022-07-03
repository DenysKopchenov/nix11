package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.PhoneRepository;
import com.alevel.lesson10.shop.repository.impl.PhoneRepositoryListImpl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PhoneService {

    private static final Random RANDOM = new Random();
    private static final PhoneRepository PHONE_REPOSITORY = new PhoneRepositoryListImpl();

    public void fillPhoneRepository() {
        for (int i = 0; i < 5; i++) {
            PHONE_REPOSITORY.save(new Phone("Title - " + RANDOM.nextInt(),
                    RANDOM.nextInt(),
                    RANDOM.nextLong(),
                    "Model - " + RANDOM.nextInt(),
                    getRandomManufacturer()));
        }
    }

    private Manufacturer getRandomManufacturer() {
        Manufacturer[] values = Manufacturer.values();
        int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        PHONE_REPOSITORY.findAll().forEach(System.out::println);
        System.out.println("=".repeat(20));
    }

    public Phone findById(String id) {
        Optional<Phone> optionalPhone = PHONE_REPOSITORY.findById(id);
        if (optionalPhone.isPresent()) {
            return optionalPhone.get();
        }
        throw new RuntimeException();
    }

    public void update(Phone phone) {
        PHONE_REPOSITORY.update(phone);
    }

    public void delete(String id) {
        PHONE_REPOSITORY.delete(id);
    }

    public List<Phone> findAll() {
        return PHONE_REPOSITORY.findAll();
    }

    public void save(Phone phone) {
        PHONE_REPOSITORY.save(phone);
    }
}
