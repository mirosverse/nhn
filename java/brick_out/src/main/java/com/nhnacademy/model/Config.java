package com.nhnacademy.model;

import com.nhnacademy.model.domain.box.BrickStatus;

import java.awt.*;

public class Config {

    // Ball
    public static final Color BALL_DEFAULT_COLOR = Color.LIGHT_GRAY;

//    // MovableBall
//    public static final int DEFAULT_DX = 0;
//    public static final int DEFAULT_DY = 0;

    // Box
    public static final Color BOX_DEFAULT_COLOR = Color.GRAY;

    // Brick
    public static final BrickStatus DEFAULT_BRICKSTATUS = BrickStatus.EASY;

    // BrickStatus
    public static final int BRICK_EASY_HP = 1;
    public static final int BRICK_EASY_SCORE = 1;

    public static final int BRICK_HARD_HP = 3;
    public static final int BRICK_HARD_SCORE = 3;

    public static final int BRICK_UNBREAKABLE_HP = -1;  // 예외 처리
    public static final int BRICK_UNBREAKABLE_SCORE = 0;

    // ControlBar
    public static final Color CONTROL_DEFAULT_COLOR = Color.GRAY;

    //PlayBoard
    public static final Color PLAYBOARD_DEFAULT_COLOR = Color.BLACK;
    public static final Color PLAYBOARD_FONT_COLOR = Color.WHITE;
    public static final int PANEL_SIZE = 20;


    // view
    public static final int DEFAULT_DT = 1;
    public static final int WALL_THICKNESS = 18;
    public static final int CONTROL_DEFAULT_WIDTH = 400;
    public static final int PLAYBOARD_THICKNESS = 18;
    public static final int BRICK_WIDTH = 48;
    public static final int BRICK_HEIGHT = 20;
    public static final int BRICK_LINES = 10;
    public static final int BRICK_MARGIN = 3;


    // controller
    public static final int FRAME_WIDTH = 500;
    public static final int FRAME_HEIGHT = 400;
    public static final int MAX_MOVE_COUNT = 0; // 값이 0이면 무한으로 처리

    public static final int BALL_RADIUS = 13;



    // GameSetting
    // EASY모드 : All bricks are EASY;
    // HARD모드 : Easy - 50%. Hard - 50%

}

// 상수 구성 - 도메인 vs 반환타입
// why. 작은 프로젝트엔 도메인끼리 묶어두는 게 더 적합한 것 같다고 생각..