package com.nhnacademy.practice;

import org.json.JSONObject;

public class Exam01 {
    public static void main(String[] args) {
        JSONObject address = new JSONObject();
        address.put("code", 13487);
        address.put("city", "Seongnam");

        JSONObject object = new JSONObject();
        object.put("address", address);
        object.put("name", "nhn");
        System.out.println(object.toString());
    }
   
}
