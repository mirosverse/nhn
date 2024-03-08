package com.nhnacademy.model.domain.box;

import com.nhnacademy.model.Config;

import java.awt.*;

public enum BrickStatus {
    EASY(Config.BRICK_EASY_HP, Config.BRICK_EASY_SCORE, Config.BRICK_EASY_COLOR),
    HARD(Config.BRICK_HARD_HP, Config.BRICK_HARD_SCORE, Config.BRICK_HARD_COLOR),
    Unbreakable(Config.BRICK_EASY_HP, Config.BRICK_EASY_HP, Config.BRICK_UNBREAKABLE_COLOR);

    private int hp;
    private int score;
    private Color color;

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

    public boolean isUnbreakble() {
        return this == Unbreakable;
    }
}
