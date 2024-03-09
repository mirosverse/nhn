package com.nhnacademy.model.domain.box;

import com.nhnacademy.model.Config;
import com.nhnacademy.model.interfaces.Breakable;

public class Brick extends Box implements Breakable {
    private int hp;
    private BrickStatus status;

    public Brick(int x, int y, int width, int height) {
        this(x, y, width, height, Config.DEFAULT_BRICKSTATUS);
    }

    public Brick(int x, int y, int width, int height, BrickStatus status) {
        super(x, y, width, height, status.getColor());
        this.status = status;
        this.hp = status.getInitHp();
    }

    public BrickStatus getStatus() {
        return status;
    }

    @Override
    public boolean isBroken() {
        return !status.isUnbreakable() && hp == 0;
    }

    public int getScore() {
        return status.getScore();
    }
}
