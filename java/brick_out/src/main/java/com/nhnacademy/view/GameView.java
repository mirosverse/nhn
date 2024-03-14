package com.nhnacademy.view;

import com.nhnacademy.controller.GameController;
import com.nhnacademy.model.Config;
import com.nhnacademy.model.domain.ball.Ball;
import com.nhnacademy.model.domain.box.*;
import com.nhnacademy.model.domain.box.Box;
import com.nhnacademy.model.interfaces.Paintable;
import com.nhnacademy.model.interfaces.Regionable;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class GameView extends JPanel {
    private GameController controller;

    public GameView() {
        setSize(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);
    }

    public void init() {
        controller = GameController.getInstance();

        // 점수판 설정
        PlayBoard playBoard = new PlayBoard(0, 0, Config.FRAME_WIDTH, Config.PLAYBOARD_THICKNESS);

        // 벽 설정
        Box topWall = new Box(Config.WALL_THICKNESS, Config.PLAYBOARD_THICKNESS, Config.FRAME_WIDTH - Config.WALL_THICKNESS * 2, Config.WALL_THICKNESS);
        Box leftWall = new Box(0, Config.PLAYBOARD_THICKNESS, Config.WALL_THICKNESS, Config.FRAME_HEIGHT - Config.PLAYBOARD_THICKNESS);
        Box rightWall = new Box(Config.FRAME_WIDTH - Config.WALL_THICKNESS, Config.PLAYBOARD_THICKNESS, Config.WALL_THICKNESS, Config.FRAME_HEIGHT - Config.PLAYBOARD_THICKNESS);

        // 컨트롤바 설정
        ControlBar controlBar = new ControlBar((Config.FRAME_WIDTH - Config.CONTROL_DEFAULT_WIDTH) / 2, Config.FRAME_HEIGHT - Config.WALL_THICKNESS, Config.CONTROL_DEFAULT_WIDTH, Config.WALL_THICKNESS);

        controller.add(playBoard);
        controller.add(topWall);
        controller.add(leftWall);
        controller.add(rightWall);
        controller.add(controlBar);
    }

    public void addBricks(List<Brick> bricks) {
        Random random = new Random();
        Brick cur;
        for (int i = 0; i < Config.BRICK_LINES; i++) {
            int x = Config.WALL_THICKNESS + Config.BRICK_MARGIN;
            int y = Config.WALL_THICKNESS + Config.PLAYBOARD_THICKNESS + Config.BRICK_MARGIN * (i + 1) + i * Config.BRICK_HEIGHT;
            while (x < Config.FRAME_WIDTH - Config.WALL_THICKNESS - Config.BRICK_WIDTH) {
                cur = bricks.remove(random.nextInt(bricks.size()));
                cur.getRegion().setLocation(x, y);
                controller.add(cur);
                x += Config.BRICK_WIDTH + Config.BRICK_MARGIN;
            }
        }
    }

    public void addBalls(List<Ball> balls){
        for (Ball ball : balls) {
            controller.add(ball);
        }
    }


    // 뷰 업데이트 코드
    public void update() {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Regionable object : controller.getRegionables()) {
            if (object instanceof Paintable) {
                ((Paintable) object).paint(g);
            }
        }
    }
}
