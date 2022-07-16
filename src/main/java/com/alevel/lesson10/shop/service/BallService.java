package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.repository.BallRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BallService {

    private static final Random RANDOM = new Random();
    private BallRepository ballRepository;

    public BallService(BallRepository ballRepository) {
        this.ballRepository = ballRepository;
    }

    public void fillBallRepository() {
        for (int i = 0; i < 5; i++) {
            ballRepository.save(createRandomBall());
        }
    }

    private Ball createRandomBall() {
        return new Ball("Title - " + RANDOM.nextInt(),
                RANDOM.nextInt(),
                RANDOM.nextLong(),
                getRandomSize());
    }

    private Size getRandomSize() {
        Size[] values = Size.values();
        int index = RANDOM.nextInt(1, values.length);
        return values[index];
    }

    public void printAll() {
        ballRepository.findAll().forEach(System.out::println);
        System.out.println("=".repeat(20));
    }

    public Ball findById(String id) {
        return ballRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Ball findByIdOrCreateDefaultBall(String id) {
        return ballRepository.findById(id).orElse(new Ball("", 0, 0, Size.NONE));
    }

    public Ball findByIdOrCreateRandomBall(String id) {
        Optional<Ball> optionalBall = ballRepository.findById(id);
        return optionalBall.orElseGet(this::createRandomBall);
    }

    public Optional<Ball> findByIdOrCreateRandomOptionalBall(String id) {
        return ballRepository.findById(id).or(() -> Optional.of(createRandomBall()));
    }

    public void update(Ball ball) {
        ballRepository.update(ball);
    }

    public void updateBallIfPresent(Ball ball) {
        ballRepository.findById(ball.getId()).ifPresent(foundedBall -> {
            foundedBall.setTitle(ball.getTitle());
            foundedBall.setPrice(1L);
            foundedBall.setSize(Size.BIG);
            ballRepository.update(foundedBall);
        });
    }

    public void delete(String id) {
        ballRepository.delete(id);
    }

    public void deleteIfPresentOrSave(Ball ball) {
        ballRepository.findById(ball.getId())
                .ifPresentOrElse(foundedBall -> ballRepository.delete(foundedBall.getId()),
                        () -> ballRepository.save(ball));
    }

    public void deleteBallIfSizeIsBig(String id) {
        ballRepository.findById(id)
                .filter(ball -> ball.getSize().equals(Size.BIG))
                .ifPresent(foundedBall -> ballRepository.delete(foundedBall.getId()));
    }

    public List<Ball> findAll() {
        return ballRepository.findAll();
    }

    public void save(Ball ball) {
        ballRepository.save(ball);
    }

    public String mapBallToString(String id) {
        return ballRepository.findById(id).map(Ball::toString).orElse("Not Found");
    }
}
