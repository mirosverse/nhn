package com.nhnacademy;

import com.nhnacademy.controller.GameController;
import com.nhnacademy.view.GameView;

/*
게임 모드는 기본적으로 *HARD* 로 설정되어 있습니다.
- 공의 종류 : EASY(Orange), HARD(Red), UNBREAKABLE(DARK GRAY)
- 바꾸고 싶다면 Config.java 파일에서 DEFAULT_MODE 를 EASY 로 바꾸십시오.
공의 속도 조절 -> DEFAULT_DT
 */

public class Main {
    public static void main(String[] args) {
        GameView view = new GameView();
        GameController controller = GameController.getInstance();
        controller.gameStart();
    }
}