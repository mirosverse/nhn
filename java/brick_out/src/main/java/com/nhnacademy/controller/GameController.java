package com.nhnacademy.controller;

import com.nhnacademy.model.Config;
import com.nhnacademy.model.domain.ball.Ball;
import com.nhnacademy.model.domain.ball.BoundedBall;
import com.nhnacademy.model.domain.box.Brick;
import com.nhnacademy.model.domain.box.BrickStatus;
import com.nhnacademy.model.domain.box.ControlBar;
import com.nhnacademy.model.domain.box.PlayBoard;
import com.nhnacademy.model.interfaces.Bounded;
import com.nhnacademy.model.interfaces.Movable;
import com.nhnacademy.model.interfaces.Regionable;
import com.nhnacademy.view.GameView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameController implements MouseMotionListener, MouseListener {
    private static GameController instance;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private List<Regionable> regionables;
    private List<Regionable> userBalls;
    private PlayBoard playBoard;
    private ControlBar controlBar;
    private GameView view;
    private JFrame frame;
    private int moveCount;
    private int maxMoveCount = 0;

    private int dragStartX;
    private int dragStartY;
    private boolean isDragging;


    private GameController() {
        regionables = new LinkedList<>();
        userBalls = new LinkedList<>();
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
        frame.setSize(Config.FRAME_WIDTH + 16, Config.FRAME_HEIGHT + 39);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(view);

        view.addBricks(createBricks(Config.DEFAULT_MODE));
        view.addBalls(createBalls(Config.DEFAULT_MODE));
        view.addMouseListener(this);
        view.addMouseMotionListener(this);

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

    public List<Ball> createBalls(GameSetting mode) {
        List<Ball> balls = new ArrayList<>();
        for (int i = 0; i < mode.getBallCount(); i++) {

            BoundedBall ball = new BoundedBall(
                    Config.FRAME_WIDTH / 2 - (mode.getBallCount() * (Config.BALL_RADIUS * 2)) / 2 + i * (Config.BALL_RADIUS * 2),
                    Config.FRAME_HEIGHT - Config.WALL_THICKNESS - Config.BALL_RADIUS,
                    Config.BALL_RADIUS);
            ball.setDX(1);
            ball.setDY(1);
            balls.add(ball);
        }
        return balls;
    }


    // 공의 이동을 담당
    public void move() {
        if ((getMaxMoveCount() == 0) || (getMoveCount() < getMaxMoveCount())) {
            for (Regionable userBall : userBalls) {     // 공들만 골라서 regionables 들과 비교
                if (userBall instanceof Movable) {
                    ((Movable) userBall).move();
                    collide(userBall);
                }
            }
        }
        moveCount++;
        view.update();
    }

    // 충돌 감지 및 처리 (제거)
    private void collide(Regionable object) {
        List<Regionable> removeList = new ArrayList<>();
        if (object instanceof Bounded) {
            for (int j = 0; j < regionables.size(); j++) {
                Regionable other = regionables.get(j);
                if (isCollision(object, other)) {
                    ((Bounded) object).bounce(other);
                    if (other instanceof ControlBar) {
                        controlBar.updateSize();
                    }
                    if (other instanceof Brick) {
                        ((Brick) other).loseHp();
                        if (((Brick) other).isBroken()) removeList.add(other);
                    }
//                     logger.info("ball({})와 ball({})이 충돌하였습니다.", object.getId(),other.getId());
                }
            }
        }
        for (Regionable regionable : removeList) {
            remove(regionable);
        }
    }

    private void remove(Regionable obj) {
        if (obj instanceof Brick) {
            playBoard.addScore(((Brick) obj).getStatus().getScore());
        }
        regionables.remove(obj);
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

        if (object instanceof BoundedBall) {
            userBalls.add(object);
        }
        if (object instanceof PlayBoard) {
            playBoard = (PlayBoard) object;
        }
        if (object instanceof ControlBar) {
            controlBar = (ControlBar) object;
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
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragStartX = e.getX();
        dragStartY = e.getY();

        if (controlBar.getRegion().contains(dragStartX, dragStartY)) {
            isDragging = true;
            logger.info("마우스 클릭...  x: ({}), y: ({})    dx: ({})", dragStartX, dragStartY);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isDragging) {
            int dx = e.getX() - dragStartX;
            controlBar.getRegion().setLocation(controlBar.getRegion().x + dx, controlBar.getRegion().y);
            // logger.info("드래그 중...  x: ({}), y: ({})    dx: ({})", controlBar.getX(), controlBar.getY(), dx);
            dragStartX = e.getX();
            dragStartY = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}