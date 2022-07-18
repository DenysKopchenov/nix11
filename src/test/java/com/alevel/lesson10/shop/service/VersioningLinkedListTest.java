package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class VersioningLinkedListTest {

    private VersioningLinkedList<Product> target;
    private Ball ball;

    @BeforeEach
    void setUp() {
        target = new VersioningLinkedList<>();
        ball = new Ball("Ball", 1, 1, Size.BIG);
    }

    @Test
    void addFirst_oneElement() {
        target.addFirst(ball);

        assertEquals(1, target.size());
    }

    @Test
    void addFirst_threeElements() {
        target.addFirst(ball);
        target.addFirst(ball);
        target.addFirst(ball);

        assertEquals(3, target.size());
    }

    @Test
    void getFirstVersionDate() throws InterruptedException {
        target.addFirst(ball);
        Thread.sleep(1);
        target.addFirst(ball);
        target.addFirst(ball);

        assertTrue(target.getFirstVersionDate().isBefore(target.getLastVersionDate()));
    }

    @Test
    void getFirstVersionDate_zeroElements() {
        assertThrows(NoSuchElementException.class, () -> target.getFirstVersionDate());
    }

    @Test
    void getLastVersionDate() throws InterruptedException {
        target.addFirst(ball);
        Thread.sleep(1);
        target.addFirst(ball);
        target.addFirst(ball);

        assertTrue(target.getLastVersionDate().isAfter(target.getFirstVersionDate()));
    }

    @Test
    void getLastVersionDate_zeroElements() {
        assertThrows(NoSuchElementException.class, () -> target.getLastVersionDate());
    }

    @Test
    void getVersionCount_oneElement() {
        target.addFirst(ball);

        assertEquals(1, target.getVersionCount());
    }

    @Test
    void getVersionCount_threeElements() {
        target.addFirst(ball);
        target.addFirst(ball);
        target.addFirst(ball);

        assertEquals(3, target.getVersionCount());
    }

    @Test
    void getVersionCount_addThreeThenRemoveOne() {
        target.addFirst(ball);
        target.addFirst(ball);
        target.addFirst(ball);
        target.deleteByVersion(1);

        assertEquals(2, target.getVersionCount());
    }

    @Test
    void findByVersion_oneElement() {
        target.addFirst(ball);

        assertEquals(ball, target.findByVersion(1));
    }

    @Test
    void findByVersion_wrongVersion() {
        assertThrows(IllegalArgumentException.class, () -> target.findByVersion(10));
    }

    @Test
    void deleteByVersion_oneElement() {
        target.addFirst(ball);

        assertEquals(1, target.size());
        assertTrue(target.deleteByVersion(1));
        assertEquals(0, target.size());
    }

    @Test
    void deleteByVersion_wrongVersion() {
        target.addFirst(ball);
        target.addFirst(ball);
        target.addFirst(ball);

        assertFalse(target.deleteByVersion(4));
    }

    @Test
    void setByVersion_returnTrue() {
        Ball setBall = new Ball("set ball", 1, 1, Size.BIG);
        target.addFirst(ball);

        assertTrue(target.setByVersion(1, setBall));
        assertEquals(setBall, target.findByVersion(1));
    }

    @Test
    void setByVersion_returnFalse() {
        Ball setBall = new Ball("set ball", 1, 1, Size.BIG);
        target.addFirst(ball);

        assertFalse(target.setByVersion(2, setBall));
    }

    @Test
    void size_threeElements() {
        target.addFirst(ball);
        target.addFirst(ball);
        target.addFirst(ball);

        assertEquals(3, target.size());
    }
}