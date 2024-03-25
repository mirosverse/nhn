package com.nhnacademy.cannon;

import com.nhnacademy.model.CannonGameConfig;
import com.nhnacademy.model.domain.Point;
import com.nhnacademy.model.domain.Vector;
import com.nhnacademy.model.domain.box.Box;
import com.nhnacademy.model.domain.ball.BoundedBall;
import com.nhnacademy.model.domain.box.Brick;
import com.nhnacademy.model.domain.box.MovableBox;
import com.nhnacademy.model.interfaces.Bounded;
import com.nhnacademy.model.interfaces.HitListener;
import com.nhnacademy.model.interfaces.Movable;
import com.nhnacademy.model.interfaces.Regionable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CannonController implements MouseMotionListener, KeyListener, ComponentListener {
    private static final int CANNON_SPEED = 10;
    private static CannonController instance;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private List<Regionable> regionables;
    private List<Regionable> boundedList;
    private MovableBox cannon;

    Vector gravity = new Vector(0, 1);
    Vector windSpeed = new Vector(0, 0);

    private CannonView view;
    private JFrame frame;
    private int moveCount;
    private int maxMoveCount = 0;

    private CannonController() {
        boundedList = new LinkedList<>();
        regionables = new LinkedList<>();
    }

    public static CannonController getInstance() {
        if (instance == null) {
            instance = new CannonController();
        }
        return instance;
    }

    public void gameStart() {
        frame = new JFrame("Cannon Game");
        frame.setSize(CannonGameConfig.FRAME_WIDTH, CannonGameConfig.FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(null);

        view = new CannonView();
        view.init();
        frame.add(view);

        // view.addBricks(createBricks(CannonGameConfig.DEFAULT_MODE));
        view.addMouseMotionListener(this);

        setMaxMoveCount(CannonGameConfig.MAX_MOVE_COUNT);
        run();
    }

    public void addButton(JButton button) {
        frame.add(button);
    }

    public void addSlider(JSlider slider) {
        frame.add(slider);
    }

    // 공의 이동을 담당
    public void move() {
        if ((getMaxMoveCount() == 0) || (getMoveCount() < getMaxMoveCount())) {
            for (Regionable userBall : boundedList) { // 공들만 골라서 regionables 들과 비교
                if (userBall instanceof BoundedBall) {
                    ((Movable) userBall).move();
                    collide((BoundedBall) userBall);
                }
            }
        }
        moveCount++;
        view.update();
    }

    // 충돌 감지 및 처리 (제거)
    private void collide(BoundedBall object) {
        List<Regionable> removeList = new ArrayList<>();

        for (int i = 0; i < boundedList.size(); i++) {
            Regionable other = boundedList.get(i);
            if (isCollision(object, other)) {
                object.bounce(other);
                if (other instanceof Brick) {
                    ((Brick) other).loseHp();
                    if (((Brick) other).isBroken()) {
                        removeList.add(other);
                    }

                }

            }
        }
        // logger.info("ball({})와 ball({})이 충돌하였습니다.", object.getId(),other.getId());

        for (Regionable other : removeList) {
            remove(other);
        }
    }

    private void remove(Regionable obj) {
        boundedList.remove(obj);
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
        // for (Regionable existObj : getRegionables()) {
        // if (isCollision(object, existObj)) {
        // throw new IllegalArgumentException();
        // }
        // }

        if (object instanceof BoundedBall || object instanceof Brick) {
            boundedList.add(object);
        } else {
            regionables.add(object);
        }
    }

    public void addCannon(MovableBox cannon) {
        this.cannon = cannon;
        add(cannon);
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            move();
            try {
                Thread.sleep(CannonGameConfig.DEFAULT_DT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public List<Regionable> getBoundedList() {
        return boundedList;
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

    public void start() {
        BoundedBall ball = new BoundedBall(cannon.getMaxX(), cannon.getMinY() - 20,
                10, Color.DARK_GRAY);

        ball.addStartedActionListener(() -> {

        });

        ball.addMovedActionListener(() -> {
            List<Regionable> removeList = new LinkedList<>();

            Vector newMotion = ball.getMotion();
            newMotion.add(gravity);
            newMotion.add(windSpeed);

            ball.setMotion(newMotion);

            if (ball instanceof Bounded) {
                for (int j = 0; j < getCount(); j++) {
                    Regionable other = get(j);

                    if (ball != other && ball.isCollision(other)) {
                        ((Bounded) ball).bounce(other);

                        if (other instanceof HitListener) {
                            ((HitListener) other).hit(ball);
                        }
                    }
                }
            }

            for (Regionable item : removeList) {
                remove(item);
            }
        });

        add(ball);
    }

    public int getCount() {
        return boundedList.size();
    }

    public Regionable get(int index) {
        return boundedList.get(index);
    }

    @Override
    public void componentResized(ComponentEvent e) {

        // if (ballList.isEmpty() && (getWidth() > BAR_WIDTH) && (getHeight() > BAR_THICKNESS)) {
        //     leftWall.setBounds(new Bounds(-WALL_THICKNESS, -WALL_THICKNESS,
        //             WALL_THICKNESS, WALL_THICKNESS * 2 + getHeight()));
        //     rightWall.setBounds(new Bounds(getWidth(), -WALL_THICKNESS,
        //             WALL_THICKNESS, WALL_THICKNESS * 2 + getHeight()));
        //     topWall.setBounds(new Bounds(-WALL_THICKNESS, -WALL_THICKNESS,
        //             getWidth() + WALL_THICKNESS * 2, WALL_THICKNESS));
        //     bottomWall.setBounds(new Bounds(-WALL_THICKNESS, getHeight(),
        //             getWidth() + WALL_THICKNESS * 2, WALL_THICKNESS));
        //     bar.moveTo(new Point(100, getHeight() - BAR_THICKNESS / 2));
        // }

    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            cannon.move(new Vector(-CANNON_SPEED, 0));
            if (cannon.getMinX() < 0) {
                cannon.setLocation(new Point(cannon.getWidth() / 2, cannon.getCenterY()));
            }
        } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            cannon.move(new Vector(CANNON_SPEED, 0));
            if (cannon.getMaxX() > CannonGameConfig.VIEW_WIDTH) {
                cannon.setLocation(new Point(CannonGameConfig.VIEW_WIDTH - cannon.getWidth() / 2, cannon.getCenterY()));
            }
        } else if (event.getKeyCode() == KeyEvent.VK_R) {
            // init();
        } else if (event.getKeyCode() == KeyEvent.VK_S) {
            start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}