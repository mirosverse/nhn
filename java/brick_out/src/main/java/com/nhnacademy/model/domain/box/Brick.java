package com.nhnacademy.model.domain.box;

import com.nhnacademy.model.Config;
import com.nhnacademy.model.interfaces.Breakable;

public class Brick extends MovableBox implements Breakable {

    private int hp;
    private BrickStatus status;

    public Brick(int x, int y, int width, int height) {
        this(x, y, width, height, Config.DEFAULT_BRICKSTATUS);
    }

    public Brick(int x, int y, int width, int height, BrickStatus status) {
        super(x, y, width, height, status.getColor());
        this.hp = status.getInitHp();
    }

    @Override
    public boolean isBroken() {
        return !status.isUnbreakble() && hp == 0;
    }

    public int getScore() {
        return status.getScore();
    }
}
