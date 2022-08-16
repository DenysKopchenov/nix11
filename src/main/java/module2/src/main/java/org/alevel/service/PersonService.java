package org.alevel.service;

import org.alevel.model.customer.Customer;

import java.util.Random;

public class PersonService {

    private static final Random RANDOM = new Random();

    public Customer generateRandomCustomer() {
        Customer customer = new Customer();
        customer.setAge(RANDOM.nextInt(6, 100));
        customer.setEmail(generateRandomEmailAddress());
        return customer;
    }

    private static String generateRandomEmailAddress() {
        StringBuilder emailAddress = new StringBuilder();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        while (emailAddress.length() < 5) {
            int character = RANDOM.nextInt(26);
            emailAddress.append(alphabet.charAt(character));
        }
        emailAddress.append(RANDOM.nextInt(96));
        emailAddress.append("@mail.com");
        return emailAddress.toString();
    }
}
