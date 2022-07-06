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
    private BallRepository ballRepository = new BallRepositoryListImpl();

    public BallService(BallRepository ballRepository) {
        this.ballRepository = ballRepository;
    }

    public void fillBallRepository() {
        for (int i = 0; i < 5; i++) {
            ballRepository.save(new Ball("Title - " + RANDOM.nextInt(),
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
        ballRepository.findAll().forEach(System.out::println);
        System.out.println("=".repeat(20));
    }

    public Ball findById(String id) {
        Optional<Ball> optionalBall = ballRepository.findById(id);
        if (optionalBall.isPresent()) {
            return optionalBall.get();
        }
        throw new RuntimeException();
    }

    public void update(Ball ball) {
        ballRepository.update(ball);
    }

    public void delete(String id) {
        ballRepository.delete(id);
    }

    public List<Ball> findAll() {
        return ballRepository.findAll();
    }

    public void save(Ball ball) {
        ballRepository.save(ball);
    }
}
