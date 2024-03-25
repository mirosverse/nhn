package com.nhnacademy;

public class Main {
    public static void main(String[] args) {
        int n = 3;
        int port = 12345;

        Bingo bingo = new Bingo(n, port);
        bingo.run();

    }
}