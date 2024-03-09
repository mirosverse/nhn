package com.nhnacademy.model.domain.box;

import com.nhnacademy.model.Config;
import com.nhnacademy.model.domain.box.Box;

public class PlayBoard extends Box {
    private static int count = 0;

    private int id;     // 1P, 2P, ...
    private int score;

    public PlayBoard(int x, int y, int width, int height) {
        super(x,y,width,height, Config.PLAYBOARD_DEFAULT_COLOR);
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
