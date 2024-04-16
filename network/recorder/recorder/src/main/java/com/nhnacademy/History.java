package com.nhnacademy;

public class History {
    private int matchCount;
    private int winCount;

    public History() {
        matchCount = 0;
        winCount = 0;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }
}
