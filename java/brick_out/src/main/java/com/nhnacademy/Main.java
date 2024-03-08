package com.nhnacademy;

import com.nhnacademy.controller.GameController;
import com.nhnacademy.view.GameView;

public class Main {
    public static void main(String[] args) {
        GameView view = new GameView();
        GameController controller = GameController.getInstance();
        controller.gameStart();
    }
}