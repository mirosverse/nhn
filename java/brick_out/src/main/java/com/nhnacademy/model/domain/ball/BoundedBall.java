package com.nhnacademy.model.domain.ball;

import com.nhnacademy.model.interfaces.Bounded;
import com.nhnacademy.model.interfaces.Regionable;

import java.awt.Color;
import java.awt.Rectangle;

public class BoundedBall extends MovableBall implements Bounded {

    public BoundedBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
    }

    public BoundedBall(int x, int y, int radius) {
        super(x, y, radius);
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public void bounce(Regionable other) {
        Rectangle intersection = getRegion().intersection(other.getRegion());

        if ((getRegion().getHeight() != intersection.getHeight())
                && (other.getRegion().getHeight() != intersection.getHeight())) {
            setDY(-getDY());
        }

        if ((getRegion().getWidth() != intersection.getWidth())
                && (other.getRegion().getWidth() != intersection.getWidth())) {
            setDX(-getDX());
        }

        // 무한반사에 빠질 시 강제로 수정
        if (intersection.getWidth() == 1) {
            if (getRegion().getX() < other.getRegion().getX() && getDX() > 0)
                setDX(-getDX());
            if (getRegion().getX() > other.getRegion().getX() && getDX() < 0)
                setDX(-getDX());
        }
        if (intersection.getHeight() == 1) {
            if (getRegion().getY() < other.getRegion().getY() && getDY() > 0)
                setDY(-getDY());
            if (getRegion().getY() > other.getRegion().getY() && getDY() < 0)
                setDY(-getDY());
        }

        // logger.info("ball({})와 Brick({})이 충돌하였습니다. intersection: ({}). dx: ({}), dy:
        // ({})", this.getId(), other.getId(), intersection.toString(), this.getDX(),
        // this.getDY());
        // logger.info("ball: ({})", this.getRegion().toString());
        // logger.info("box: ({})", other.getRegion().toString());
    }

    @Override
    public boolean isCollision(Regionable other) {
        return getRegion().intersects(other.getRegion());
    }
}