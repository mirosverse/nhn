package com.nhnacademy.controller;

import com.nhnacademy.model.Config;
import com.nhnacademy.model.domain.ball.Ball;
import com.nhnacademy.model.domain.ball.BoundedBall;
import com.nhnacademy.model.domain.ball.MovableBall;
import com.nhnacademy.model.domain.box.Brick;
import com.nhnacademy.model.domain.box.BrickStatus;
import com.nhnacademy.model.interfaces.Bounded;
import com.nhnacademy.model.interfaces.Movable;
import com.nhnacademy.model.interfaces.Regionable;
import com.nhnacademy.view.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GameController implements MouseMotionListener {
    private static GameController instance;

    private List<Regionable> regionables;
    private GameView view;
    private JFrame frame;
    private int moveCount;
    private int maxMoveCount = 0;

    private GameController() {
        regionables = new LinkedList<>();
        view = new GameView();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void gameStart() {
        view.init();
        frame = new JFrame("Brick Out");
        Insets insets = frame.getInsets();
        frame.setSize(Config.FRAME_WIDTH + 10, Config.FRAME_HEIGHT + 39);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(view);

        view.addBricks(createBricks(GameSetting.EASY));


        setMaxMoveCount(Config.MAX_MOVE_COUNT);
        run();
    }

    public List<Brick> createBricks(GameSetting mode) {
        List<Brick> bricks = new ArrayList<>();
        int numOfBricksX = (Config.FRAME_WIDTH - Config.WALL_THICKNESS * 2) / (Config.BRICK_WIDTH + Config.BRICK_MARGIN);

        for (int i = 0; i < numOfBricksX * Config.BRICK_LINES * mode.getEasyBrickRatio(); i++) {
            bricks.add(new Brick(0, 0, Config.BRICK_WIDTH, Config.BRICK_HEIGHT, BrickStatus.EASY));
        }
        for (int i = 0; i < numOfBricksX * Config.BRICK_LINES * mode.getHardBrickRatio(); i++) {
            bricks.add(new Brick(0, 0, Config.BRICK_WIDTH, Config.BRICK_HEIGHT, BrickStatus.HARD));
        }
        for (int i = 0; i < numOfBricksX * Config.BRICK_LINES * mode.getUnbreakableBrickRatio(); i++) {
            bricks.add(new Brick(0, 0, Config.BRICK_WIDTH, Config.BRICK_HEIGHT, BrickStatus.Unbreakable));
        }
        return bricks;
    }


    // 공의 이동을 담당
    public void move() {
        if ((getMaxMoveCount() == 0) || (getMoveCount() < getMaxMoveCount())) {
            for (int i = 0; i < regionables.size(); i++) {
                Regionable object = regionables.get(i);
                if (object instanceof MovableBall) {
                    ((Movable) object).move();
                    collide(object);
                }
            }
        }
        moveCount++;
        view.update();
    }

    // 충돌 감지 및 처리
    private void collide(Regionable object) {
        if (object instanceof BoundedBall) {
            for (int j = 0; j < regionables.size(); j++) {
                Regionable other = regionables.get(j);

                if (isCollision(object, other)) {
                    ((Bounded) object).bounce(other);
                    // logger.info("ball({})와 ball({})이 충돌하였습니다.", object.getId(),otherBall.getId());
                }
            }
        }
    }

    // 충돌 감지
    public boolean isCollision(Regionable o1, Regionable o2) {
        return !o1.equals(o2) && o1.getRegion().intersects(o2.getRegion());
    }

    // Ball or Box 추가
    public void add(Regionable object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
        if ((object.getX() - object.getRegion().getWidth() / 2 < 0)
                || (object.getX() + object.getRegion().getWidth() / 2 > view.getWidth())
                || (object.getY() + object.getRegion().getHeight() / 2 > view.getHeight())) {
            throw new IllegalArgumentException();
        }
        for (Regionable existObj : getRegionables()) {
            if (isCollision(object, existObj)) {
                throw new IllegalArgumentException();
            }
        }
        regionables.add(object);
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            move();
            try {
                Thread.sleep(Config.DEFAULT_DT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public List<Regionable> getRegionables() {
        return regionables;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getMaxMoveCount() {
        return maxMoveCount;
    }

    public void setMaxMoveCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }
        maxMoveCount = count;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
