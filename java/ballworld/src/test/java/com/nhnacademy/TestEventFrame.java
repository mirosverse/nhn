package com.nhnacademy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class TestEventFrame extends JFrame implements KeyListener {

    public TestEventFrame(){
        addKeyListener(this);
        addMouseMotionListener(null);
    }

    public static void main(String[] args) {
        TestEventFrame frame = new TestEventFrame();
        frame.setSize(600, 500);
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}
