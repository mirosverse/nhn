package com.nhnacademy.model.domain.box;

import java.awt.*;

public enum BrickStatus {
    EASY(1, 1, Color.YELLOW),
    HARD(3, 5, Color.ORANGE),
    Unbreakable(-1, 0, Color.DARK_GRAY);

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
