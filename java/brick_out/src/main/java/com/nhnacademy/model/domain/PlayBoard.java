package com.nhnacademy.model.domain;

public class PlayBoard {
    private static int count = 0;

    private int id;     // 1P, 2P, ...
    private int score;

    public PlayBoard() {
        this.id = ++count;
        this.score = 0;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int plusScore) {
        this.score += plusScore;
    }
}
