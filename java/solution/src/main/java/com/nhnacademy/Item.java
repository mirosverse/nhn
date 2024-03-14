package com.nhnacademy;

public class Item {
    private static int count = 0;
    private String name;
    private int id;

    public Item(String name) {
        this.name = name;
        this.id = ++count;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
