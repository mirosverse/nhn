package com.nhnacademy;

public enum Type {
    CONNECT("connect"),
    MESSAGE("message");

    private String name;

    private Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
