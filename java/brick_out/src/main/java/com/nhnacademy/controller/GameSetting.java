package com.nhnacademy.controller;

public enum GameSetting {
    EASY(1,0,0),
    HARD(0.8,0.2,0.1);

    private double easyBrickRatio;
    private double hardBrickRatio;
    private double unbreakableBrickRatio;

    GameSetting(double easyBrickRatio, double hardBrickRatio, double unbreakableBrickRatio) {
        this.easyBrickRatio = easyBrickRatio;
        this.hardBrickRatio = hardBrickRatio;
        this.unbreakableBrickRatio = unbreakableBrickRatio;
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
}
