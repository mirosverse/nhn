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
            JSONArray userArray = new JSONArray();
            for (User user : users) {
                JSONObject userObject = new JSONObject();
                userObject.put("id", user.getId());
                userObject.put("nickname", user.getNickname());
                userObject.put("userItems", user.getItems());
                userArray.put(userObject);
            }

            gameDB.put("users", userArray);
            // gameDB.put("users", users);
            gameDB.put("items", items);
            FileWriter gameFile = new FileWriter(
                    "/Users/zei/workspace/nhn/java/daily/recorder/src/main/java/com/nhnacademy/game_db.json");
            gameFile.write(gameDB.toString());
            gameFile.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        users.add(user);
        for (Item item : items) {
            user.addItem(item);
        }
    }

    public void deletedUser(User user) {
        users.remove(user);
    }

    public void addItem(Item item) {
        items.add(item);
        for (User user : users) {
            user.addItem(item);
        }
    }

    public void printUserDetail() {
        for (User user : users) {
            System.out.println(user);
            for (Item item : user.getItems()) {
                System.out.println(item);
            }
        }
    }

    public void printUsers() {
        System.out.println("ID          NAME");
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
                    "/Users/zei/workspace/nhn/java/daily/recorder/src/main/java/com/nhnacademy/game_db.json");
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
        if (gameDB.isEmpty() || gameDB.isNull("users"))
            return;

        JSONArray userArray = gameDB.getJSONArray("users");
        for (int i = 0; i < userArray.length(); i++) {
            JSONObject userObject = userArray.getJSONObject(i);
            User user = new User(
                    userObject.getString("id"), userObject.getString("nickname"));
            JSONArray itemArray = userObject.getJSONArray("userItems");
            for (int j = 0; j < itemArray.length(); j++) {
                JSONObject itemObject = itemArray.getJSONObject(j);
                user.addItem(new Item(
                        itemObject.getString("id"),
                        itemObject.getString("model"),
                        itemObject.getInt("health"),
                        itemObject.getInt("attack"),
                        itemObject.getInt("defense"),
                        itemObject.getInt("speed"),
                        itemObject.getInt("attackSpeed")));
            }

            users.add(user);
        }

        if (gameDB.isNull("items"))
            return;

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
