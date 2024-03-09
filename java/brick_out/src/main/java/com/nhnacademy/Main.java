package com.nhnacademy;

import com.nhnacademy.controller.GameController;
import com.nhnacademy.view.GameView;

/*
주의 ! ) 하드모드 진행시, 공이 회색 벽돌(깨질수없음)에 닿으면 가끔 진행되지 않는 오류가 있습니다. 그냥 재시작 해주십시오.
- 아마 bounce 내부적으로 dx, dy 값과 충돌이 일어나는 것 같음.
 */
public class Main {
    public static void main(String[] args) {
        GameView view = new GameView();
        GameController controller = GameController.getInstance();
        controller.gameStart();
    }
}