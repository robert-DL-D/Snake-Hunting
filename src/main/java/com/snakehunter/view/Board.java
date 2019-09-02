package com.snakehunter.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class Board
        extends JPanel {
    private static final int X_MARGIN = 20;
    private static final int Y_MARGIN = 20;

    public Board() {
        setSize(440, 440);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((i + j) % 2 == 0) {
                    graphics.setColor(Color.YELLOW);
                } else {
                    graphics.setColor(Color.ORANGE);
                }
                graphics.fillRect(20 + 40 * i, 20 + 40 * j, 40, 40);
            }
        }

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < 100; i++) {
            graphics.drawString("" + (i + 1), getX(i + 1), getY(i + 1) + 20);
        }

    }

    private int getX(int pos) {
        pos--;
        if ((pos / 10) % 2 == 0) {
            return X_MARGIN + 10 + pos % 10 * 40;
        } else {
            return X_MARGIN + 370 - pos % 10 * 40;
        }
    }

    private int getY(int pos) {
        pos--;
        return Y_MARGIN - 30 + 400 - pos / 10 * 40;
    }
}
