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
        super(x,y, radius);
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

    }

    @Override
    public boolean isCollision(Regionable other) {
        return getRegion().intersects(other.getRegion());
    }
}