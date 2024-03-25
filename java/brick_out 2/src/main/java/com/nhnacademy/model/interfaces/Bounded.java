package com.nhnacademy.model.interfaces;


public interface Bounded {

    void bounce(Regionable other);

    boolean isCollision(Regionable other);
    
}
