package com.nhnacademy;

public class User {
    private static int count = 0;
    private int id = ++count;
    private int[][] board;

    public int getId() {
        return id;
    }
}
