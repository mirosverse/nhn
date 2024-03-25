package com.nhnacademy.practice;

import org.json.JSONObject;

public class Exam05 {
    public static void main(String[] args) {
        JSONObject object = new JSONObject();

        object.put("name", "nhn");
        object.put("age", 10);

        Object name = object.get("name");
        System.out.println("name type : " + name.getClass().getTypeName());
        if (name instanceof String) {
            System.out.println("Name is String");
        }
        Object age = object.get("age");
        System.out.println("age type : " + age.getClass().getTypeName());
    }
}
