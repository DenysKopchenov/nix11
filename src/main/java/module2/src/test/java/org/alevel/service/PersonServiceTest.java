package org.alevel.service;

import org.alevel.model.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonService();
    }

    @Test
    void generateRandomCustomer() {
        Customer customer = personService.generateRandomCustomer();

        assertNotNull(customer);
    }

}