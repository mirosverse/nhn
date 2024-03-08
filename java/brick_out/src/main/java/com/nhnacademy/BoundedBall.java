package com.nhnacademy;

import java.awt.Color;
import java.awt.Rectangle;

public class BoundedBall extends MovableBall implements Bounded{

    // Rectangle bounds;

    public BoundedBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
    }

    // public void setBounds(Rectangle bounds) {
    //     this.bounds = bounds;
    // }

    // public Rectangle getBounds() {
    //     return bounds;
    // }

    // public boolean isOutOfBounds() {
    //     Rectangle region = new Rectangle(getX() - getRadius(), getY() - getRadius(), 2 * getRadius(), 2 * getRadius());
    //     Rectangle intersection = bounds.intersection(region);

    //     return (intersection.getWidth() != region.getWidth()) || (intersection.getHeight() != region.getHeight());
    // }

    @Override
    public void move() {
        super.move();

        // if (isOutOfBounds()) {
        //     bounce();
        // }
    }

    // public void bounce() {
    //     if ((getX() - getRadius() < getBounds().getMinX())
    //             || (getX() + getRadius() > getBounds().getMaxX())) {

    //         setDX(-getDX());
    //     }

    //     if ((getY() - getRadius() < getBounds().getMinY())
    //             || (getY() + getRadius() > getBounds().getMaxY())) {
    //         setDY(-getDY());
    //     }
    // }

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