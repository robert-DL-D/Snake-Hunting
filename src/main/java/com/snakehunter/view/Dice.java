package com.snakehunter.view;

import com.snakehunter.view.GameView.GameViewListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Random;


/**
 * @author WeiYi Yu
 * @date 2019-08-24
 */
public class Dice
        extends ClickablePanel {

    private static final int DICE_LENGTH = 45;
    private static final int DOT_LENGTH = 5;

    public static final int DICE_NUM_MIN = 1;
    public static final int DICE_NUM_MAX = 6;

    private GameViewListener listener;

    private Random random;
    private int lastNum = 1;

    public Dice() {
        setSize(DICE_LENGTH, DICE_LENGTH);
        random = new Random();
        addMouseListener(this);
    }

    public void setListener(GameViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (listener != null) {
            listener.onDiceClick();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setColor(Color.BLACK);
        graphics.fill3DRect(0, 0, DICE_LENGTH, DICE_LENGTH, false);
        graphics.setColor(Color.WHITE);

        if (lastNum == 1) {
            graphics.fillOval(20, 20, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 2) {
            graphics.fillOval(10, 20, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 20, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 3) {
            graphics.fillOval(20, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 4) {
            graphics.fillOval(10, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 5) {
            graphics.fillOval(10, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(20, 20, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 6) {
            graphics.fillOval(10, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 10, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(10, 20, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 20, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            graphics.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        }
    }

    public int getLastNum() {
        return lastNum;
    }

    public void roll() {
        new Thread(() -> {
            int num;

            // fake dice animation
            for (int i = 1; i <= 20; i++) {
                do {
                    num = getRandomNumber(1, 6);
                } while (lastNum == num);
                lastNum = num;

                repaint();
                // This creates the fake dice roll animation
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    System.out.println("Dice roll exception " + e);
                }
            }

            listener.onDiceRolled(lastNum);
        }).start();

    }

    private int getRandomNumber(int min, int max) {
        return random.nextInt(max) + min;
    }
}