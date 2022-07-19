package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.repository.BallRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

class OptionalExamplesTest {

    private OptionalExamples target;
    private BallRepository ballRepository;

    @BeforeEach
    void setUp() {
        ballRepository = Mockito.mock(BallRepository.class);
        target = new OptionalExamples(ballRepository);
    }

    @Test
    void findByIdOrCreateDefaultBall_present() {
        Ball ball = createBall();
        when(ballRepository.findById(ball.getId())).thenReturn(Optional.of(ball));
        Ball foundedBall = target.findByIdOrCreateDefaultBall(ball.getId());

        verify(ballRepository).findById(ball.getId());
        Assertions.assertEquals("title", foundedBall.getTitle());
    }

    @Test
    void findByIdOrCreateDefaultBall_noBall() {
        Ball ball = createBall();
        Ball foundedBall = target.findByIdOrCreateDefaultBall(ball.getId());

        verify(ballRepository).findById(ball.getId());
        Assertions.assertEquals("", foundedBall.getTitle());
        Assertions.assertEquals(0, foundedBall.getCount());
        Assertions.assertEquals(0, foundedBall.getPrice());
        Assertions.assertEquals(Size.NONE, foundedBall.getSize());
    }

    @Test
    void findByIdOrCreateRandomBall_present() {
        Ball ball = createBall();
        when(ballRepository.findById(ball.getId())).thenReturn(Optional.of(ball));
        Ball foundedBall = target.findByIdOrCreateRandomBall(ball.getId());

        verify(ballRepository).findById(ball.getId());
        Assertions.assertEquals("title", foundedBall.getTitle());
        Assertions.assertEquals(1, foundedBall.getCount());
        Assertions.assertEquals(2, foundedBall.getPrice());
        Assertions.assertEquals(Size.SMALL, foundedBall.getSize());
    }

    @Test
    void findByIdOrCreateRandomBall_noBall() {
        Ball ball = createBall();
        Ball foundedBall = target.findByIdOrCreateRandomBall(ball.getId());

        verify(ballRepository).findById(ball.getId());
        Assertions.assertNotEquals(ball.getId(), foundedBall.getId());
    }

    @Test
    void findByIdOrCreateRandomOptionalBall_present() {
        Ball ball = createBall();
        when(ballRepository.findById(ball.getId())).thenReturn(Optional.of(ball));

        Optional<Ball> foundedBallOptional = target.findByIdOrCreateRandomOptionalBall(ball.getId());
        verify(ballRepository).findById(ball.getId());
        Assertions.assertEquals(ball.getId(), foundedBallOptional.get().getId());
    }

    @Test
    void findByIdOrCreateRandomOptionalBall_noBall() {
        Ball ball = createBall();

        Optional<Ball> foundedBallOptional = target.findByIdOrCreateRandomOptionalBall(ball.getId());
        verify(ballRepository).findById(ball.getId());
        Assertions.assertNotEquals(ball.getId(), foundedBallOptional.get().getId());
    }

    @Test
    void updateBallIfPresent_present() {
        Ball ball = createBall();
        when(ballRepository.findById(ball.getId())).thenReturn(Optional.of(ball));
        ball.setTitle("Updated");
        target.updateBallIfPresent(ball);

        verify(ballRepository).update(ball);
        Assertions.assertEquals("Updated", ballRepository.findById(ball.getId()).get().getTitle());
    }

    @Test
    void updateBallIfPresent_noBall() {
        Ball ball = createBall();
        target.updateBallIfPresent(ball);

        verify(ballRepository).findById(ball.getId());
        verify(ballRepository, times(0)).update(ball);
    }

    @Test
    void deleteIfPresentOrSave_present() {
        Ball ball = createBall();
        when(ballRepository.findById(ball.getId())).thenReturn(Optional.of(ball));
        target.deleteIfPresentOrSave(ball);

        verify(ballRepository).findById(ball.getId());
        verify(ballRepository).delete(ball.getId());
        verify(ballRepository, times(0)).save(ball);
    }

    @Test
    void deleteIfPresentOrSave_noBall() {
        Ball ball = createBall();
        target.deleteIfPresentOrSave(ball);

        verify(ballRepository).findById(ball.getId());
        verify(ballRepository, times(0)).delete(ball.getId());
        verify(ballRepository).save(ball);
    }

    @Test
    void deleteBallIfSizeIsBig_present() {
        Ball ball = new Ball("title", 1, 2, Size.BIG);
        when(ballRepository.findById(ball.getId())).thenReturn(Optional.of(ball));
        target.deleteBallIfSizeIsBig(ball.getId());

        verify(ballRepository).findById(ball.getId());
        verify(ballRepository).delete(ball.getId());
    }

    @Test
    void deleteBallIfSizeIsBig_noBall() {
        Ball ball = new Ball("title", 1, 2, Size.BIG);
        target.deleteBallIfSizeIsBig(ball.getId());

        verify(ballRepository).findById(ball.getId());
        verify(ballRepository, times(0)).delete(ball.getId());
    }

    @Test
    void mapBallToString_present() {
        Ball ball = createBall();
        when(ballRepository.findById(ball.getId())).thenReturn(Optional.of(ball));

        String actual = target.mapBallToString(ball.getId());
        verify(ballRepository).findById(ball.getId());
        Assertions.assertEquals(ball.toString(), actual);
    }

    @Test
    void mapBallToString_noBall() {
        Ball ball = createBall();

        String actual = target.mapBallToString(ball.getId());
        verify(ballRepository).findById(ball.getId());
        Assertions.assertEquals("Not Found", actual);
    }

    private Ball createBall() {
        return new Ball("title", 1, 2, Size.SMALL);
    }

}