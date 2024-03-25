package com.nhnacademy;

import com.nhnacademy.cannon.CannonController;

public class Main {
    public static void main(String[] args) {
        CannonController controller = CannonController.getInstance();
        controller.gameStart();
    }
}