package com.nhnacademy.cannon;

import com.nhnacademy.model.CannonGameConfig;
import com.nhnacademy.model.domain.Vector;
import com.nhnacademy.model.domain.ball.Ball;
import com.nhnacademy.model.domain.box.*;
import com.nhnacademy.model.domain.box.Box;
import com.nhnacademy.model.interfaces.Movable;
import com.nhnacademy.model.interfaces.Paintable;
import com.nhnacademy.model.interfaces.Regionable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class CannonView extends JPanel  {
    private CannonController controller;

    public CannonView() {
        setBounds(200, 0, CannonGameConfig.FRAME_WIDTH - 200, CannonGameConfig.FRAME_HEIGHT - 120);
        setBackground(Color.WHITE);

    }

    public void init() {
        controller = CannonController.getInstance();

        // 벽 설정
        Box topWall = new Box(0, -CannonGameConfig.PLAYBOARD_THICKNESS, CannonGameConfig.VIEW_WIDTH,
                CannonGameConfig.WALL_THICKNESS);
        Box leftWall = new Box(-CannonGameConfig.WALL_THICKNESS, 0, CannonGameConfig.WALL_THICKNESS,
                CannonGameConfig.VIEW_HEIGHT);
        Box rightWall = new Box(CannonGameConfig.VIEW_WIDTH, 0, CannonGameConfig.WALL_THICKNESS,
                CannonGameConfig.VIEW_HEIGHT);
        Box bottomWall = new Box(0, CannonGameConfig.VIEW_HEIGHT, CannonGameConfig.VIEW_WIDTH,
                CannonGameConfig.WALL_THICKNESS);

        bottomWall.setHitListener(other -> {
            if (other instanceof Regionable) {
                Vector motion = ((Movable) other).getMotion();
                motion.multiply(0.5);
                ((Movable) other).setMotion(motion);
            }
        });

        // 대포 설정
        MovableBox cannon = new MovableBox(0, CannonGameConfig.VIEW_HEIGHT - 30, 60, 30);
        controller.addCannon(cannon);

        // fire Button
        JButton fireButton = new JButton();
        fireButton.setBounds(10, CannonGameConfig.FRAME_HEIGHT - 95, 80, 50);
        fireButton.setText("Fire!");
        controller.addButton(fireButton);
        fireButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.start();
            }
        });

        // clear Button
        JButton clearButton = new JButton();
        clearButton.setBounds(100, CannonGameConfig.FRAME_HEIGHT - 95, 80, 50);
        clearButton.setText("Clear!");
        controller.addButton(clearButton);

        // 속도 슬라이더
        JSlider windSpeed = new JSlider(0, 1000, 500);
        windSpeed.setBounds(5, 20, 200, 100);
        windSpeed.setPaintTrack(true);
        windSpeed.setPaintTicks(true);
        windSpeed.setPaintLabels(true);
        windSpeed.setMajorTickSpacing(200);
        windSpeed.setMinorTickSpacing(50);
        controller.addSlider(windSpeed);

        controller.add(topWall);
        controller.add(leftWall);
        controller.add(rightWall);
        controller.add(bottomWall);
    }

    public void addBricks(List<Brick> bricks) {
        Random random = new Random();
        Brick cur;
        for (int i = 0; i < CannonGameConfig.BRICK_LINES; i++) {
            int x = CannonGameConfig.WALL_THICKNESS + CannonGameConfig.BRICK_MARGIN;
            int y = CannonGameConfig.WALL_THICKNESS + CannonGameConfig.PLAYBOARD_THICKNESS
                    + CannonGameConfig.BRICK_MARGIN * (i + 1) + i * CannonGameConfig.BRICK_HEIGHT;
            while (x < CannonGameConfig.VIEW_WIDTH - CannonGameConfig.WALL_THICKNESS - CannonGameConfig.BRICK_WIDTH) {
                cur = bricks.remove(random.nextInt(bricks.size()));
                cur.getRegion().setLocation(x, y);
                controller.add(cur);
                x += CannonGameConfig.BRICK_WIDTH + CannonGameConfig.BRICK_MARGIN;
            }
        }
    }

    public void addBalls(List<Ball> balls) {
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
        for (Regionable object : controller.getBoundedList()) {
            if (object instanceof Paintable) {
                ((Paintable) object).paint(g);
            }
        }

    }

}
