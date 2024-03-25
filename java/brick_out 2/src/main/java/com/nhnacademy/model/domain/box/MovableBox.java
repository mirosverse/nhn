package com.nhnacademy.model.domain.box;

import java.awt.Color;

import com.nhnacademy.model.domain.Point;
import com.nhnacademy.model.domain.Vector;
import com.nhnacademy.model.interfaces.Movable;
import com.nhnacademy.model.interfaces.MovedActionListener;
import com.nhnacademy.model.interfaces.StartedActionListener;


public class MovableBox extends Box implements Movable {
    public static final int DEFAULT_DX = 0;
    public static final int DEFAULT_DY = 0;
    long dt = 0;
    boolean stopped = true;
    StartedActionListener startedActionListener;
    MovedActionListener movedActionListener;
    Thread thread;

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

    public void move() {
        move(motion);
    }

    public void stop() {
        stopped = true;
        if (thread != null) {
            thread.interrupt();
        }
    }

    public void move(Vector motion) {
        Point origin = getLocation();
        origin.translate(motion);
        setLocation(origin);

        if (movedActionListener != null) {
            movedActionListener.action();
        }
    }

    public void moveTo(Point location) {
        setLocation(location);

        if (movedActionListener != null) {
            movedActionListener.action();
        }
    }

    @Override
    public void setDT(long dt) {
        this.dt = dt;
    }

    @Override
    public long getDT() {
        return dt;
    }

    @Override
    public void run() {
        thread = Thread.currentThread();
        stopped = false;

        if (startedActionListener != null) {
            startedActionListener.action();
        }

        while (!stopped) {
            try {
                move();
                Thread.sleep(dt);
            } catch (InterruptedException ignore) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void addStartedActionListener(StartedActionListener listener) {
        this.startedActionListener = listener;
    }

    @Override
    public void addMovedActionListener(MovedActionListener listener) {
        this.movedActionListener = listener;
    }

    public void setMotion(int dx, int dy) {
        motion.set(dx, dy);
    }

    public void setMotion(Vector newMotion) {
        motion.set(newMotion);
    }

}
