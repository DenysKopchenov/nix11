package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.repository.BallRepository;
import com.alevel.lesson10.shop.repository.impl.BallRepositoryListImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.mockito.Mockito.*;

class BallServiceTest {

    private BallService target;
    private BallRepository ballRepository;

    @BeforeEach
    void setUp() {
        ballRepository = mock(BallRepositoryListImpl.class);
        target = new BallService(ballRepository);
    }

    @Test
    void createAndFillRepository() {
        target.createAndFillRepository(5);
        verify(ballRepository, times(5)).save(any());
    }

    @Test
    void printAll() {
        target.printAll();
        verify(ballRepository).findAll();
    }

    @Test
    void findById() {
        Ball ball = new Ball("title", 1, 2, Size.SMALL);
        when(ballRepository.findById(argThat(s -> {
            Assertions.assertEquals("123", s);
            return true;
        }))).thenReturn(Optional.of(ball));

        Ball actualBall = target.findById("123");
        verify(ballRepository).findById(anyString());
        Assertions.assertEquals("title", actualBall.getTitle());
    }

    @Test()
    void findById_callRealMethod() {
        ballRepository = spy(BallRepositoryListImpl.class);
        when(ballRepository.findById("123")).thenCallRealMethod();

        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findById("123"));
    }

    @Test
    void findById_wrongId() {
        when(ballRepository.findById("123")).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findById("123"));
        verify(ballRepository).findById("123");
    }

    @Test
    void findById_present() {
        Ball ball = createBall();
        when(ballRepository.findById(ball.getId())).thenReturn(Optional.of(ball));
        Ball foundedBall = target.findById(ball.getId());

        verify(ballRepository).findById(ball.getId());
        Assertions.assertEquals(ball.getId(), foundedBall.getId());
    }

    @Test
    void findById_noBall() {
        Ball ball = createBall();
        when(ballRepository.findById(ball.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findById(ball.getId()));
        verify(ballRepository).findById(ball.getId());
    }

    @Test
    void update() {
        Ball ball = createBall();
        target.save(ball);
        ball.setTitle("updated");
        target.update(ball);

        ArgumentCaptor<Ball> argumentCaptor = ArgumentCaptor.forClass(Ball.class);
        verify(ballRepository).update(argumentCaptor.capture());
        Assertions.assertEquals("updated", argumentCaptor.getValue().getTitle());
    }


    @Test
    void delete() {
        target.delete("123");
        verify(ballRepository).delete("123");
    }

    @Test
    void findAll() {
        target.findAll();

        verify(ballRepository).findAll();
    }

    @Test
    void save() {
        Ball ball = createBall();
        target.save(ball);

        verify(ballRepository).save(ball);
    }

    private Ball createBall() {
        return new Ball("title", 1, 2, Size.SMALL);
    }
}