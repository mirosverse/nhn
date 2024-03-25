package com.nhnacademy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class DB {
    JSONObject gameDB;
    private List<User> users;
    private List<Item> items;

    public DB() {
        gameDB = new JSONObject();
        this.users = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public void save() {
        try {
            gameDB.put("users", users);
            gameDB.put("items", items);
            FileWriter gameFile = new FileWriter(
                    "/Users/zei/workspace/nhn/java/daily/0319/db/src/main/java/com/nhnacademy/game_db.json");
            gameFile.write(gameDB.toString());
            gameFile.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void deletedUser(User user) {
        users.remove(user);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void printUsers() {
        for (User user : users) {
            System.out.println(user);
        }
    }

    public void printItems() {
        for (Item item : items) {
            System.out.println(item);
        }
    }

    public void read() {
        // 파일 읽기
        try {
            FileReader gameFile = new FileReader(
                    "/Users/zei/workspace/nhn/java/daily/0319/db/src/main/java/com/nhnacademy/game_db.json");
            StringBuilder gameSb = new StringBuilder();
            int ch;
            while ((ch = gameFile.read()) != -1) {
                gameSb.append((char) ch);
            }
            gameFile.close();
            gameDB = new JSONObject(gameSb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 객체에 입력
        JSONArray userArray = gameDB.getJSONArray("users");
        for (int i = 0; i < userArray.length(); i++) {
            JSONObject userObject = userArray.getJSONObject(i);
            users.add(new User(
                    userObject.getString("id"), userObject.getString("nickname")));
        }

        JSONArray itemArray = gameDB.getJSONArray("items");
        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject itemObject = itemArray.getJSONObject(i);
            items.add(new Item(
                    itemObject.getString("id"),
                    itemObject.getString("model"),
                    itemObject.getInt("health"),
                    itemObject.getInt("attack"),
                    itemObject.getInt("defense"),
                    itemObject.getInt("speed"),
                    itemObject.getInt("attackSpeed")));
        }

    }

}
