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
        if (product != null) {
            balls.add(product);
            LOGGER.info("Ball {} saved", product.getId());
        } else {
            throw new IllegalArgumentException("Ball can not be null");
        }
    }

    @Override
    public void saveAll(List<Ball> products) {
        if (products != null) {
            balls.addAll(products);
        } else {
            throw new IllegalArgumentException("List can not be null");
        }
    }

    @Override
    public List<Ball> findAll() {
        return balls;
    }

    @Override
    public Optional<Ball> findById(String id) {
        return balls.stream()
                .filter(ball -> ball.getId().equals(id))
                .findFirst();
    }

    @Override
    public void update(Ball product) {
        Optional<Ball> optionalBall = findById(product.getId());
        if (optionalBall.isPresent()) {
            Ball ball = optionalBall.get();
            ball.setTitle(product.getTitle());
            ball.setCount(product.getCount());
            ball.setPrice(product.getPrice());
        } else {
            throw new IllegalArgumentException("No ball found");
        }
    }

    @Override
    public void delete(String id) {
        if (balls.removeIf(ball -> ball.getId().equals(id))) {
            LOGGER.info("Ball {} deleted", id);
        }
    }
}
