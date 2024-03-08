package com.nhnacademy.model.domain.box;

import com.nhnacademy.model.Vector;
import com.nhnacademy.model.interfaces.Breakable;
import com.nhnacademy.model.interfaces.Movable;

import java.awt.*;

public class MovableBox extends Box implements Movable {
    public static final int DEFAULT_DX = 0;
    public static final int DEFAULT_DY = 0;

    final Vector motion = new Vector();

    public MovableBox(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public MovableBox(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
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
