package com.nhnacademy;

public enum Color {
    RED, BLUE;

    @Override
    public String toString() {
        return this == RED ? "RED" : "BLUE";
    }
}
