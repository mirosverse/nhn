package com.nhnacademy.model.domain.box;

import com.nhnacademy.model.Config;
import com.nhnacademy.model.domain.Point;
import com.nhnacademy.model.interfaces.HitListener;
import com.nhnacademy.model.interfaces.Paintable;
import com.nhnacademy.model.interfaces.Regionable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.UUID;

public class Box implements Regionable, Paintable, HitListener {

    final String id = UUID.randomUUID().toString();

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private Color color;
    private final Rectangle region;
    HitListener hitListener;

    public Box(int x, int y, int width, int height) {
        this(x, y, width, height, Config.BOX_DEFAULT_COLOR);
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

        // logger.trace("Box created : {}, {}", x, y);
    }

    public String getId() {
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

    public void setLocation(Point location) {
        region.setLocation(location.getX() - getWidth() / 2, location.getY() - getHeight() / 2);
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

    public Point getLocation() {
        return new Point((int) region.getCenterX(), (int) region.getCenterY());
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

    @Override
    public void hit(Regionable other) {
        if (hitListener != null) {
            hitListener.hit(other);
        }
    }

    @Override
    public void setHitListener(HitListener listener) {
        this.hitListener = listener;
    }

    @Override
    public int getMinX() {
        return (int) region.getMinX();
    }

    @Override
    public int getMaxY() {
        return (int) region.getMaxY();

    }

    @Override
    public int getMaxX() {
        return (int)region.getMaxX();
    }

    @Override
    public int getMinY() {
        return (int) region.getMinY();
    }

    public int getCenterY() {
        return (int) region.getCenterY();
    }

}
