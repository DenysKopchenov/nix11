package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.PhoneRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PhoneService {

    private static final Random RANDOM = new Random();
    private PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public void fillPhoneRepository() {
        for (int i = 0; i < 5; i++) {
            phoneRepository.save(new Phone("Title - " + RANDOM.nextInt(),
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
        phoneRepository.findAll().forEach(System.out::println);
        System.out.println("=".repeat(20));
    }

    public Phone findById(String id) {
        Optional<Phone> optionalPhone = phoneRepository.findById(id);
        if (optionalPhone.isPresent()) {
            return optionalPhone.get();
        }
        throw new RuntimeException();
    }

    public void update(Phone phone) {
        phoneRepository.update(phone);
    }

    public void delete(String id) {
        phoneRepository.delete(id);
    }

    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

    public void save(Phone phone) {
        phoneRepository.save(phone);
    }
}
