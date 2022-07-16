package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.phone.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.PhoneRepository;
import com.alevel.lesson10.shop.repository.impl.BallRepositoryListImpl;
import com.alevel.lesson10.shop.repository.impl.PhoneRepositoryListImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class PhoneServiceTest {

    private PhoneService target;
    private PhoneRepository phoneRepository;

    @BeforeEach
    void setUp() {
        phoneRepository = mock(PhoneRepositoryListImpl.class);
        target = new PhoneService(phoneRepository);
    }

    @Test
    void createAndFillRepository() {
        target.createAndFillRepository(5);
        verify(phoneRepository, times(5)).save(any());
    }

    @Test
    void printAll() {
        target.printAll();
        verify(phoneRepository).findAll();
    }

    @Test
    void findById() {
        Phone phone = new Phone("title", 1, 2, "model", Manufacturer.APPLE);
        when(phoneRepository.findById(argThat(s -> {
            Assertions.assertEquals("123", s);
            return true;
        }))).thenReturn(Optional.of(phone));

        Phone actualPhone = target.findById("123");
        verify(phoneRepository).findById(anyString());
        Assertions.assertEquals("title", actualPhone.getTitle());
    }

    @Test
    void findById_callRealMethod() {
        phoneRepository = spy(PhoneRepositoryListImpl.class);
        when(phoneRepository.findById("1")).thenCallRealMethod();
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findById("1"));
    }

    @Test
    void findById_wrongId() {
        when(phoneRepository.findById("1")).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findById("1"));
    }

    @Test
    void update() {
        Phone phone = new Phone("title", 1, 2, "model", Manufacturer.APPLE);
        target.save(phone);
        phone.setTitle("updated");
        target.update(phone);

        ArgumentCaptor<Phone> argumentCaptor = ArgumentCaptor.forClass(Phone.class);
        verify(phoneRepository).update(argumentCaptor.capture());
        Assertions.assertEquals("updated", argumentCaptor.getValue().getTitle());
    }


    @Test
    void delete() {
        target.delete("123");
        verify(phoneRepository).delete("123");
    }


    @Test
    void findAll() {
        target.findAll();

        verify(phoneRepository).findAll();
    }

    @Test
    void save() {
        Phone phone = new Phone("title", 1, 2, "model", Manufacturer.APPLE);
        target.save(phone);

        verify(phoneRepository).save(phone);
    }
}