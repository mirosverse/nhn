package com.nhnacademy;

import java.awt.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Box implements Regionable{

    static int count = 0;
    int id = ++count;
    Rectangle region;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    public Box(int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("0보다 커야 합니다.");
        }

        if ((x + (long)(width/2) > Integer.MAX_VALUE)
                || (x - (long)(width/2)< Integer.MIN_VALUE)
                || (y + (long)(height/2) > Integer.MAX_VALUE)
                || (y - (long) (height / 2) < Integer.MIN_VALUE)) {
            throw new IllegalArgumentException("박스가 정수 공간을 벗어납니다.");
        }

        this.region = new Rectangle(x, y, width, height);

        logger.trace("Box created : {}, {}", x, y);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return (int) region.getCenterX();
    }

    public int getY() {
        return (int) region.getCenterY();
    }

    void setX(int x) {
        region.setLocation(x , getY() );
    }

    void setY(int y) {
        region.setLocation(getX(), y );
    }

    public int getWidth() {
        return (int) region.getWidth();
    }

    public int getHeight() {
        return (int) region.getHeight();
    }

    public Rectangle getRegion() {
        return region;
    }

    public boolean isCollision(Ball ball) {
        return getRegion().intersects(ball.getRegion());
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %d, %d)", getX(), getY(), getWidth(), getHeight());
    }
}