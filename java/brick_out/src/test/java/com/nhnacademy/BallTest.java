package com.nhnacademy;

import com.nhnacademy.model.domain.ball.Ball;
import com.nhnacademy.model.domain.ball.BoundedBall;
import com.nhnacademy.model.domain.ball.MovableBall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    @Test
    void move_dx_dy만큼_이동() {
        assertDoesNotThrow(()->{
            MovableBall ball = new MovableBall(10,10,4);
            ball.setDX(3);
            ball.setDY(-1);
            ball.move();
            assertEquals(13, ball.getX());
            assertEquals(9, ball.getY());
        });
    }

    @Test
    void bounce_고정된_위치의_볼과_만나서_바운스() {
        assertDoesNotThrow(()->{
            BoundedBall ball = new BoundedBall(10,10,4); // x: 6~14,
            ball.setDX(1);
            ball.setDY(1);
            Ball fixed = new Ball(15,9,2);  // x: 12~17, y: 7~11
            assertTrue(ball.isCollision(fixed));
            ball.bounce(fixed);
            assertEquals(-1, ball.getDX());
            assertEquals(1, ball.getDY());
        });
    }

    @Test
    void isCollision() {
        BoundedBall ball1 = new BoundedBall(10,10,2);
        BoundedBall ball2 = new BoundedBall(13,10,2);
        assertTrue(ball1.isCollision(ball2));

    }
}