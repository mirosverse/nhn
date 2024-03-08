package com.nhnacademy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TestWorld {
    static final int FRAME_WIDTH = 800;
    static final int FRAME_HEIGHT = 800;
    static final int WALL_WIDTH = 700;
    static final int WALL_HEIGHT = 100;
    static final int MIN_RADIUS = 10;
    static final int MAX_RADIUS = 50;
    static final int FIXED_BALL_COUNT = 0;
    static final int BOUNDED_BALL_COUNT = 5;
    static final int MIN_DELTA = 5;
    static final int MAX_DELTA = 7;
    static final int MAX_MOVE_COUNT = 0;
    static final int DT = 10;
    static final Color[] COLOR_TABLE = {
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.YELLOW
    };

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MovableWorld world = new MovableWorld();
        world.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.add(world);

        Random random = new Random();


        PaintableBox topWall = new PaintableBox(0, 0, WALL_WIDTH, WALL_HEIGHT, Color.BLACK);
        PaintableBox bottomWall = new PaintableBox(0, FRAME_HEIGHT - WALL_HEIGHT, WALL_WIDTH, WALL_HEIGHT, Color.BLACK);
        PaintableBox leftWall = new PaintableBox(0, WALL_HEIGHT, WALL_HEIGHT, WALL_WIDTH - WALL_HEIGHT, Color.BLACK);
        PaintableBox rightWall = new PaintableBox(FRAME_WIDTH - WALL_HEIGHT, 0, WALL_HEIGHT, WALL_WIDTH, Color.BLACK);

        world.add(topWall);
        world.add(bottomWall);
        world.add(leftWall);
        world.add(rightWall);
        
        
        while (world.getBallCount()< BOUNDED_BALL_COUNT + 4) {
            try {
                BoundedBall ball = new BoundedBall(random.nextInt(FRAME_WIDTH), random.nextInt(FRAME_HEIGHT),
                        MIN_RADIUS + random.nextInt(MAX_RADIUS - MIN_RADIUS + 1),
                        COLOR_TABLE[random.nextInt(COLOR_TABLE.length)]);

                int dx = MIN_DELTA - random.nextInt(MAX_DELTA - MIN_DELTA + 1);
                int dy = MIN_DELTA - random.nextInt(MAX_DELTA - MIN_DELTA + 1);

                ball.setDX(dx);
                ball.setDY(dy);
                // ball.setBounds(world.getBounds());
                world.add(ball);
            } catch (IllegalArgumentException ignore) {
                //
            }
        }

        frame.setVisible(true);

        world.setMaxMoveCount(MAX_MOVE_COUNT);
        world.setDT(DT);
        world.run();
    }
}