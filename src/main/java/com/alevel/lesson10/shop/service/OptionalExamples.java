package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.repository.BallRepository;

import java.util.Optional;
import java.util.Random;

public class OptionalExamples {

    private BallRepository ballRepository;
    private static final Random RANDOM = new Random();

    public OptionalExamples(BallRepository ballRepository) {
        this.ballRepository = ballRepository;
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

    public void updateBallIfPresent(Ball ball) {
        ballRepository.findById(ball.getId()).ifPresent(foundedBall -> {
            foundedBall.setTitle(ball.getTitle());
            foundedBall.setPrice(1L);
            foundedBall.setSize(Size.BIG);
            ballRepository.update(foundedBall);
        });
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

    public String mapBallToString(String id) {
        return ballRepository.findById(id).map(Ball::toString).orElse("Not Found");
    }
}
