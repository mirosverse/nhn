package com.nhnacademy;

import com.nhnacademy.controller.GameController;
import com.nhnacademy.view.GameView;

/*
게임 모드는 기본적으로 *HARD* 로 설정되어 있습니다.
- 공의 종류 : EASY(Orange), HARD(Red), UNBREAKABLE(DARK GRAY)
- 바꾸고 싶다면 Config.java 파일에서 DEFAULT_MODE 를 *EASY* 로 변경하십시오.

이 외에도 게임설정을 변경하고 싶다면 Config 또는 GameSetting 을 변경한다.
- 공의 속도 조절 -> Config.DEFAULT_DT
- 공의 개수 조절 -> GameSetting
- 컨트롤바 줄어드는 속도 -> Config.CONTROL_WIDTH_DECREASE
 */

public class Main {
    public static void main(String[] args) {
        GameView view = new GameView();
        GameController controller = GameController.getInstance();
        controller.gameStart();
    }
}