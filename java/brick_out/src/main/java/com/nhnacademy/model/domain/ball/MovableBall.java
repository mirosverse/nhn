package com.nhnacademy.model.domain.ball;

import com.nhnacademy.model.interfaces.Movable;
import com.nhnacademy.model.Vector;

import java.awt.Color;


public class MovableBall extends Ball implements Movable {
    final Vector motion = new Vector();

    public MovableBall(int x, int y, int radius) {
        super(x, y, radius);
    }

    public MovableBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
    }

    public Vector getMotion() {
        return motion;
    }

    public int getDX() {
        return motion.getDX();
    }

    public int getDY() {
        return motion.getDY();
    }

    public void setDX(int dx) {
        motion.setDX(dx);
    }

    public void setDY(int dy) {
        motion.setDY(dy);
    }

    @Override
    public void move() {
        moveTo(getX() + getDX(), getY() + getDY());
//        logger.trace("{} : {}, {}, {}, {}", getId(), getX(), getY(), getRegion().getX(), getRegion().getY());
    }

    public void moveTo(int x, int y) {
        setX(x);
        setY(y);
    }
}