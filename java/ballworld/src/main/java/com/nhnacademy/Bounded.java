package com.nhnacademy;

import java.awt.Rectangle;

public interface Bounded {
    // public Rectangle getBounds();

    public Rectangle getRegion();

    public boolean isCollision(Regionable other);

    public void bounce(Regionable otherBall);

}