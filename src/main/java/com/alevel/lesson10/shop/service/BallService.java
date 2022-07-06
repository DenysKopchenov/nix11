package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.repository.impl.BallRepositoryListImpl;
import com.alevel.lesson10.shop.repository.BallRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BallService {

    private static final Random RANDOM = new Random();
    private static final BallRepository BALL_REPOSITORY = new BallRepositoryListImpl();

    public void fillBallRepository() {
        for (int i = 0; i < 5; i++) {
            BALL_REPOSITORY.save(new Ball("Title - " + RANDOM.nextInt(),
                    RANDOM.nextInt(),
                    RANDOM.nextLong(),
                    getRandomSize()));
        }
    }

    private Size getRandomSize() {
        Size[] values = Size.values();
        int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        BALL_REPOSITORY.findAll().forEach(System.out::println);
        System.out.println("=".repeat(20));
    }

    public Ball findById(String id) {
        Optional<Ball> optionalBall = BALL_REPOSITORY.findById(id);
        if (optionalBall.isPresent()) {
            return optionalBall.get();
        }
        throw new RuntimeException();
    }

    public void update(Ball ball) {
        BALL_REPOSITORY.update(ball);
    }

    public void delete(String id) {
        BALL_REPOSITORY.delete(id);
    }

    public List<Ball> findAll() {
        return BALL_REPOSITORY.findAll();
    }

    public void save(Ball ball) {
        BALL_REPOSITORY.save(ball);
    }
}
