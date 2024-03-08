package com.nhnacademy;

import java.awt.Color;
import java.awt.Rectangle;

public class BoundedBox extends MovableBox implements Bounded {

    Rectangle bounds;

    public BoundedBox(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public BoundedBox(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean isOutOfBounds() {
        Rectangle region = new Rectangle(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
        Rectangle intersection = bounds.intersection(region);

        return (intersection.getWidth() != region.getWidth()) || (intersection.getHeight() != region.getHeight());
    }

    @Override
    public void bounce(Regionable other) {
        Rectangle intersection = getRegion().intersection(other.getRegion());
        if (getRegion().getHeight() != intersection.getHeight()) {
            setDY(-getDY());
        }
        if (getRegion().getWidth() != intersection.getWidth()) {
            setDX(-getDX());
        }
    }


    @Override
    public boolean isCollision(Regionable other) {
        return getRegion().intersects(other.getRegion());
    }

}
