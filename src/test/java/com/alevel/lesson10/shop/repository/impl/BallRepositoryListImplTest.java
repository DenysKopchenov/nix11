package com.alevel.lesson10.shop.repository.impl;

import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.repository.BallRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class BallRepositoryListImplTest {

    private BallRepository target;
    private Ball ball;
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        target = new BallRepositoryListImpl();
        ball = new Ball("Title - " + random.nextInt(),
                random.nextInt(),
                random.nextLong(),
                getRandomSize());
    }

    private Size getRandomSize() {
        Size[] values = Size.values();
        int index = random.nextInt(values.length);
        return values[index];
    }

    @Test
    void save() {
        target.save(ball);
        List<Ball> balls = target.findAll();
        Ball actualBall = balls.get(0);
        Assertions.assertEquals(1, balls.size());
        Assertions.assertEquals(ball.getId(), actualBall.getId());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void saveAll_singleBall() {
        target.saveAll(List.of(ball));
        List<Ball> balls = target.findAll();
        Assertions.assertEquals(1, balls.size());
    }

    @Test
    void saveAll_manyBalls() {
        target.saveAll(List.of(ball, ball));
        List<Ball> balls = target.findAll();
        Assertions.assertEquals(2, balls.size());
    }

    @Test
    void saveAll_manyBalls_hasNull() {
        List<Ball> balls = new ArrayList<>();
        balls.add(ball);
        balls.add(null);
        target.saveAll(balls);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void saveAll_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(null));
    }

    @Test
    void saveAll_empty() {
        target.saveAll(Collections.emptyList());
        List<Ball> balls = target.findAll();
        Assertions.assertEquals(0, balls.size());
    }

    @Test
    void findAll() {
        target.saveAll(List.of(ball, ball));
        List<Ball> balls = target.findAll();
        Assertions.assertEquals(2, balls.size());
    }

    @Test
    void findById() {
        target.save(ball);
        Optional<Ball> actualBall = target.findById(ball.getId());
        Assertions.assertTrue(actualBall.isPresent());
    }

    @Test
    void findById_wrongId() {
        target.save(ball);
        Optional<Ball> actualBall = target.findById("123");
        Assertions.assertTrue(actualBall.isEmpty());
    }

    @Test
    void update() {
        String newTitle = "New Title";
        target.save(ball);
        ball.setTitle(newTitle);
        target.update(ball);

        List<Ball> balls = target.findAll();

        Assertions.assertEquals(newTitle, balls.get(0).getTitle());
        Assertions.assertEquals(ball.getId(), balls.get(0).getId());
        Assertions.assertEquals(ball.getSize(), balls.get(0).getSize());
    }

    @Test
    void update_noBall() {
        target.save(ball);

        Assertions.assertThrows(IllegalArgumentException.class, () -> target.update(new Ball("Title", 1, 1, Size.SMALL)));

        List<Ball> balls = target.findAll();
        Assertions.assertEquals(1, balls.size());
        Assertions.assertEquals(ball.getId(), balls.get(0).getId());
    }

    @Test
    void delete() {
        target.save(ball);
        target.delete(ball.getId());

        List<Ball> balls = target.findAll();

        Assertions.assertTrue(balls.isEmpty());
    }

    @Test
    void delete_noBall() {
        target.save(ball);
        target.delete("123");

        List<Ball> balls = target.findAll();

        Assertions.assertEquals(1, balls.size());
    }
}