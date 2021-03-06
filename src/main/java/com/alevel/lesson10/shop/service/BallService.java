package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.repository.ProductRepository;

public class BallService extends AbstractProductService<Ball> {

    public BallService(ProductRepository<Ball> repository) {
        super(repository);
    }

    @Override
    protected Ball createProduct() {
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


}
