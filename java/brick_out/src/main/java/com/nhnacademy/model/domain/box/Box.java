package com.nhnacademy.model.domain.box;

import com.nhnacademy.model.interfaces.Paintable;
import com.nhnacademy.model.interfaces.Regionable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class Box implements Regionable, Paintable {

    public static final Color DEFAULT_COLOR = Color.ORANGE;
    static int count = 0;
    int id = ++count;

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private Color color;
    private final Rectangle region;

    public Box(int x, int y, int width, int height) {
        this(x, y, width, height, DEFAULT_COLOR);
    }

    public Box(int x, int y, int width, int height, Color color) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("0보다 커야 합니다.");
        }

        if ((x + (long) (width / 2) > Integer.MAX_VALUE)
                || (x - (long) (width / 2) < Integer.MIN_VALUE)
                || (y + (long) (height / 2) > Integer.MAX_VALUE)
                || (y - (long) (height / 2) < Integer.MIN_VALUE)) {
            throw new IllegalArgumentException("박스가 정수 공간을 벗어납니다.");
        }

        this.region = new Rectangle(x, y, width, height);
        this.color = color;

//        logger.trace("Box created : {}, {}", x, y);
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
        region.setLocation(x, getY());
    }

    void setY(int y) {
        region.setLocation(getX(), y);
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

    public boolean isCollision(Regionable obj) {
        return getRegion().intersects(obj.getRegion());
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %d, %d)", getX(), getY(), getWidth(), getHeight());
    }

    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color
     * @throws IllegalArgumentException color는 null 허용하지 않습니다
     */
    public void setColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException();
        }

        this.color = color;
    }

    @Override
    public void paint(Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }

        Color originalColor = g.getColor();

        g.setColor(getColor());
        g.fillRect((int) getRegion().getX(), (int) getRegion().getY(), (int) getRegion().getWidth(),
                (int) getRegion().getHeight());

        g.setColor(originalColor);
    }
}
