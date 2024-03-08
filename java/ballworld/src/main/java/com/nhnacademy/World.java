package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Random;
import java.util.LinkedList;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class World extends JPanel implements MouseListener{
    List<Regionable> regionableList = new LinkedList<>();
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    public World() {
        super();
        addMouseListener(this);
    }

    public void add(Regionable object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }

        if ((object.getX() - object.getRegion().getWidth() / 2 < 0)
                || (object.getX() + object.getRegion().getWidth() / 2 > getWidth())
                || (object.getY() + object.getRegion().getHeight() / 2 > getHeight())) {
            throw new IllegalArgumentException();
        }

        for (Regionable existBox : regionableList) {

            if (object.getRegion().intersects(existBox.getRegion())) {
                throw new IllegalArgumentException();
            }
        }

        regionableList.add(object);
    }

    public void remove(Ball ball) {
        regionableList.remove(ball);
    }

    public void remove(Box box) {
        regionableList.remove(box);
    }

    public void removeBall(int index) {
        regionableList.remove(index);
    }

    public void removeBox(int index) {
        regionableList.remove(index);
    }

    public int getBallCount() {
        return regionableList.size();
    }

    public int getBoxCount() {
        return regionableList.size();
    }

    public Regionable getObject(int index) {
        return regionableList.get(index);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Regionable object : regionableList) {
            if (object instanceof Paintable) {
                ((Paintable) object).paint(g);
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {    
        Random random = new Random();
        MovableBall ball= new MovableBall(e.getX(), e.getY(), 50, Color.BLACK);
        ball.setDX(-10+random.nextInt(20));
        ball.setDY(-10+random.nextInt(20));
        add(ball);
    }
}