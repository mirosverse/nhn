package com.nhnacademy.practice;

import org.json.JSONArray;
import org.json.JSONObject;

public class Exam06 {
    public static void main(String[] args) {
        String[] birds = {"갈매기", "참새", "펭귄"};
        String[] animals = {"사자", "호랑이", "말"};

        
        JSONObject object = new JSONObject();
        object.put("조류", new JSONArray(new String[] { "갈매기", "참새", "펭귄" }));
        object.put("포유류", new JSONArray(new String[]{"사자", "호랑이", "말"}));
        JSONArray array = new JSONArray().put(object);
        JSONObject result = new JSONObject();
        result.put("동물", array);
        System.out.println(result);
    }
}
