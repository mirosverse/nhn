package com.nhnacademy.practice;

import org.json.JSONObject;

public class Exam02 {
    public static void main(String[] args) {
        try {
            String jsonAddress = "{ \"address\":{\"code\":\"13487\",\"city\":\"Seongnam\"},\"name\":\"nhn\"}";
            JSONObject adressObject = new JSONObject(jsonAddress);
            System.out.println(adressObject);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
