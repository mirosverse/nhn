package com.nhnacademy.model.domain.box;

import com.nhnacademy.model.Config;

import java.awt.*;

public enum BrickStatus {
    EASY(Config.BRICK_EASY_HP, Config.BRICK_EASY_SCORE, Color.ORANGE),
    HARD(Config.BRICK_HARD_HP, Config.BRICK_HARD_SCORE, Color.RED),
    Unbreakable(Config.BRICK_UNBREAKABLE_HP, Config.BRICK_UNBREAKABLE_SCORE, Color.DARK_GRAY);

    private final int hp;
    private final int score;
    private final Color color;

    BrickStatus(int hp, int score, Color color) {
        this.hp = hp;
        this.score = score;
        this.color = color;
    }

    public int getInitHp() {
        return hp;
    }

    public Color getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public boolean isUnbreakable() {
        return this == Unbreakable;
    }
}
