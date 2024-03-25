package com.nhnacademy.model.interfaces;

import java.awt.*;

public interface Regionable {
    public int getX();

    public int getY();

    Rectangle getRegion();

    String getId();

    public void hit(Regionable other);

    public void setHitListener(HitListener listener);

    public int getHeight();

    public int getMinY();

    public int getMaxY();

    public int getWidth();

    public int getMinX();

    public int getMaxX();
}
