package com.nhnacademy;

import java.util.ArrayList;
import java.util.List;

public class User {
    String id;
    String nickname;
    private List<Item> items;
    private History history;

    public User(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        items = new ArrayList<>();
        history = new History();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Item> getItems() {
        return items;
    }

    public History getHistory() {
        return history;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void setHistory(History history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "id: " + id + ", nickname: " + nickname;
    }
}
