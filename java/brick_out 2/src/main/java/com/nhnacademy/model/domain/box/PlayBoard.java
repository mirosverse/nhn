package com.nhnacademy.model.domain.box;

import com.nhnacademy.model.Config;

import java.awt.*;

public class PlayBoard extends Box {
    private static int count = 0;

    private int id;     // 1P, 2P, ...
    private int score;

    public PlayBoard(int x, int y, int width, int height) {
        super(x, y, width, height, Config.PLAYBOARD_DEFAULT_COLOR);
        this.id = ++count;
        this.score = 0;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int plusScore) {
        this.score += plusScore;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);  // 부모 클래스의 paint() 메서드 호출 (박스 그리기)

        Font font = new Font("Arial", Font.BOLD, 14);  // 폰트 설정
        g.setFont(font);

        Color originalColor = g.getColor();

        // id 표시
        int x = Config.PANEL_SIZE / 2;  // 왼쪽에서 약간의 여백을 두고 표시
        int y = Config.PLAYBOARD_THICKNESS / 2 + 6;  // 박스의 세로 중간에 표시
        g.setColor(Config.PLAYBOARD_FONT_COLOR);
        g.drawString(getId() + "P", x, y);

        // score 표시
        x = Config.FRAME_WIDTH - Config.PANEL_SIZE - g.getFontMetrics().stringWidth("Score: " + getScore());  // 오른쪽 끝에 표시
        g.drawString("Score: " + getScore(), x, y);

        g.setColor(originalColor);
    }

}
