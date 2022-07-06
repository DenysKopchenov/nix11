package com.alevel.lesson10.shop.repository.impl;

import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.repository.BallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BallRepositoryListImpl implements BallRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BallRepositoryListImpl.class);

    private final List<Ball> balls;

    public BallRepositoryListImpl() {
        balls = new ArrayList<>();
    }

    @Override
    public void save(Ball product) {
        balls.add(product);
        LOGGER.info("Ball {} saved", product.getId());
    }

    @Override
    public void saveAll(List<Ball> products) {
        balls.addAll(products);
    }

    @Override
    public List<Ball> findAll() {
        return List.copyOf(balls);
    }

    @Override
    public Optional<Ball> findById(String id) {
        return balls.stream()
                .filter(ball -> ball.getId().equals(id))
                .findFirst();
    }

    @Override
    public void update(Ball product) {
        balls.stream()
                .filter(ball -> ball.getId().equals(product.getId()))
                .findFirst().ifPresent(ball -> {
                    ball.setTitle(product.getTitle());
                    ball.setCount(product.getCount());
                    ball.setPrice(product.getPrice());
                    ball.setSize(product.getSize());
                });
    }

    @Override
    public void delete(String id) {
        if (balls.removeIf(ball -> ball.getId().equals(id))) {
            LOGGER.info("Ball {} deleted", id);
        }
    }
}
