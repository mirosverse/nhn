package com.nhnacademy.view;

import com.nhnacademy.controller.GameController;
import com.nhnacademy.model.Config;
import com.nhnacademy.model.domain.box.Box;
import com.nhnacademy.model.interfaces.Paintable;
import com.nhnacademy.model.interfaces.Regionable;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    private GameController controller;

    public GameView(){
        setSize(Config.FRAME_WIDTH, Config.FRAME_HEIGHT);

    }

    public void init(){
        controller = GameController.getInstance();

        // 벽 설정
        Box topWall = new Box(Config.WALL_THICKNESS, 0, Config.FRAME_WIDTH - Config.WALL_THICKNESS * 2, Config.WALL_THICKNESS);
//        Box bottomWall = new Box(Config.WALL_THICKNESS, Config.FRAME_HEIGHT - Config.WALL_THICKNESS * 2, Config.FRAME_WIDTH - Config.WALL_THICKNESS * 2, Config.WALL_THICKNESS);
        Box leftWall = new Box(0, 0, Config.WALL_THICKNESS, Config.FRAME_HEIGHT);
        Box rightWall = new Box(Config.FRAME_WIDTH - Config.WALL_THICKNESS, 0, Config.WALL_THICKNESS, Config.FRAME_HEIGHT);

        controller.add(topWall);
//        controller.add(bottomWall);
        controller.add(leftWall);
        controller.add(rightWall);
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
