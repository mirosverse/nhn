package com.nhnacademy.model.domain.box;

import com.nhnacademy.model.Config;

import java.awt.*;

public class ControlBar extends Box{


    public ControlBar(int x, int y, int width, int height) {
        this(x, y, width, height, Config.CONTROL_DEFAULT_COLOR);
    }

    public ControlBar(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    public void updateSize(){
        if(getRegion().getWidth()<0 || getRegion().getHeight()<0){
            throw new IllegalStateException();
        }
        // 나중에 구현
    }
}
