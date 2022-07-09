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
    void fillBallRepository() {
        target.fillBallRepository();
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
    void findById_realMethod() {
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
    void updateBallIfPresent_present() {
        Ball ball = createBall();
        when(ballRepository.findById(ball.getId())).thenReturn(Optional.of(ball));
        ball.setTitle("Updated");
        ball.setSize(Size.BIG);
        target.updateBallIfPresent(ball);

        verify(ballRepository).update(ball);
        Assertions.assertEquals("Updated", target.findById(ball.getId()).getTitle());
        Assertions.assertEquals(Size.BIG, target.findById(ball.getId()).getSize());
    }

    @Test
    void updateBallIfPresent_noBall() {
        Ball ball = createBall();
        target.updateBallIfPresent(ball);

        verify(ballRepository).findById(ball.getId());
        verify(ballRepository, times(0)).update(ball);
    }

    @Test
    void delete() {
        target.delete("123");
        verify(ballRepository).delete("123");
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