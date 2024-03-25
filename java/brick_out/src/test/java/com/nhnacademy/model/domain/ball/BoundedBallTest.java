package com.nhnacademy.model.domain.ball;

import com.nhnacademy.model.domain.box.Brick;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BoundedBallTest {

    @Test
    void isCollision() {

        BoundedBall ball = new BoundedBall(10, 10, 2);
        Brick brick = new Brick(12, 12, 2, 2);
        Rectangle intersection = ball.getRegion().intersection(brick.getRegion());

        assertFalse(ball.isCollision(brick));

    }

    @Test
    void isCollision_false() {

        BoundedBall ball = new BoundedBall(9, 9, 2);
        Brick brick = new Brick(14, 14, 2, 2);
        Rectangle intersection = ball.getRegion().intersection(brick.getRegion());

        assertFalse(ball.isCollision(brick));



    }
}
