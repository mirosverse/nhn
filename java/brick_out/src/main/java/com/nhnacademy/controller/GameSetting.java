package com.nhnacademy.controller;

public enum GameSetting {
    EASY(1, 0, 0, 1),
    HARD(0.8, 0.2, 0.1, 2);

    private final double easyBrickRatio;
    private final double hardBrickRatio;
    private final double unbreakableBrickRatio;
    private final int ballCount;

    GameSetting(double easyBrickRatio, double hardBrickRatio, double unbreakableBrickRatio, int ballCount) {
        this.easyBrickRatio = easyBrickRatio;
        this.hardBrickRatio = hardBrickRatio;
        this.unbreakableBrickRatio = unbreakableBrickRatio;
        this.ballCount = ballCount;
    }

    public double getEasyBrickRatio() {
        return easyBrickRatio;
    }

    public double getHardBrickRatio() {
        return hardBrickRatio;
    }

    public double getUnbreakableBrickRatio() {
        return unbreakableBrickRatio;
    }

    public int getBallCount() {
        return ballCount;
    }
}
