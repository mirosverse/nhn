package com.nhnacademy.practice;

import org.json.JSONTokener;

public class Exam09 {
    
    public static void main(String[] args) {
        JSONTokener tokener = new JSONTokener(System.in);
        while (!tokener.end()) {
            Object object =  tokener.nextValue();
            System.out.println(object);
        }
    }
}
