package com.alevel.lesson10.shop.service;


import com.alevel.lesson10.shop.model.laptop.CPU;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.repository.LaptopRepository;
import com.alevel.lesson10.shop.repository.impl.LaptopRepositoryListImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class LaptopServiceTest {

    private LaptopService target;
    private LaptopRepository laptopRepository;

    @BeforeEach
    void setUp() {
        laptopRepository = mock(LaptopRepositoryListImpl.class);
        target = new LaptopService(laptopRepository);
    }

    @Test
    void fillLaptopRepository() {
        target.fillLaptopRepository();
        verify(laptopRepository, times(5)).save(any());
    }

    @Test
    void printAll() {
        target.printAll();
        verify(laptopRepository).findAll();
    }

    @Test
    void findById() {
        Laptop laptop = new Laptop("title", 1, 2, CPU.APPLE);
        when(laptopRepository.findById(argThat(s -> {
            Assertions.assertEquals("123", s);
            return true;
        }))).thenReturn(Optional.of(laptop));

        Laptop actualLaptop = target.findById("123");
        verify(laptopRepository).findById(anyString());
        Assertions.assertEquals("title", actualLaptop.getTitle());
    }

    @Test
    void findById_emptyOptional() {
        when(laptopRepository.findById(anyString())).thenCallRealMethod();
        Assertions.assertThrows(RuntimeException.class, () -> target.findById(anyString()));
        verify(laptopRepository).findById(anyString());
    }

    @Test
    void findById_wrongId() {
        when(laptopRepository.findById(anyString())).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> target.findById(anyString()));
        verify(laptopRepository).findById(anyString());
    }

    @Test
    void update() {
        Laptop laptop = new Laptop("title", 1, 2, CPU.APPLE);
        target.save(laptop);
        laptop.setTitle("updated");
        target.update(laptop);

        ArgumentCaptor<Laptop> argumentCaptor = ArgumentCaptor.forClass(Laptop.class);
        verify(laptopRepository).update(argumentCaptor.capture());
        Assertions.assertEquals("updated", argumentCaptor.getValue().getTitle());
    }


    @Test
    void delete() {
        target.delete("123");
        verify(laptopRepository).delete("123");
    }


    @Test
    void findAll() {
        target.findAll();

        verify(laptopRepository).findAll();
    }

    @Test
    void save() {
        Laptop laptop = new Laptop("title", 1, 2, CPU.APPLE);
        target.save(laptop);

        verify(laptopRepository).save(laptop);
    }
}