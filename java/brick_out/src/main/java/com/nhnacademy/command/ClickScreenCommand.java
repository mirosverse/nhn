package com.nhnacademy.command;

import com.nhnacademy.controller.GameController;
import com.nhnacademy.model.domain.ball.MovableBall;
import com.nhnacademy.model.interfaces.Movable;

import javax.swing.*;
import java.util.List;

public class ClickScreenCommand implements Command{
    private GameController controller = GameController.getInstance();

    @Override
    public void execute() {
        controller.run();
    }
}
