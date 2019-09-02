package com.snakehunter.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class Board
        extends JPanel
        implements Runnable {
    private static final int X_MARGIN = 20;
    private static final int Y_MARGIN = 20;

    private double factor = 0.2;

    private Map<Integer, Integer> snakes = new HashMap<>();

    public Board() {
        setSize(440, 440);
        new Thread(this).start();
    }

    public void addSnake(int head, int tail) {
        snakes.put(head, tail);
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

        for (Integer head : snakes.keySet()) {
            drawSnake(graphics, head, snakes.get(head));
        }
    }

    public void drawSnake(Graphics g, int head, int tail) {
        int x1 = getX(head);
        int y1 = getY(head);
        int x2 = getX(tail);
        int y2 = getY(tail);

        int steps = (int) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)) / 150 * 18 + 24;


        double xstep = (double) (x2 - x1) / (steps + 1);
        double ystep = (double) (y2 - y1) / (steps + 1);

        double inc;
        double x = x1, y = y1;

        boolean odd = true;
        for (int i = 0; i < steps + 1; i++) {
            if ((i % 12) % 12 == 0) {
                inc = 0;
            } else if ((i % 12) % 11 == 0) {
                inc = 10 * factor;
            } else if ((i % 12) % 10 == 0) {
                inc = 13 * factor;
            } else if ((i % 12) % 9 == 0) {
                inc = 15 * factor;
            } else if ((i % 12) % 8 == 0) {
                inc = 13 * factor;
            } else if ((i % 12) % 7 == 0) {
                inc = 10 * factor;
            } else if ((i % 12) % 6 == 0) {
                inc = 0 * factor;
            } else if ((i % 12) % 5 == 0) {
                inc = -10 * factor;
            } else if ((i % 12) % 4 == 0) {
                inc = -13 * factor;
            } else if ((i % 12) % 3 == 0) {
                inc = -15 * factor;
            } else if ((i % 12) % 2 == 0) {
                inc = -13 * factor;
            } else {
                inc = -10 * factor;
            }
            x += xstep;
            y += ystep;
            if (odd) {
                g.setColor(Color.BLACK);
                odd = false;
            } else {
                g.setColor(Color.GREEN);
                odd = true;
            }
            if (x2 > x1) {
                g.fillOval((int) (x + inc), (int) (y - inc), 20 - 10 * i / steps, 20 - 10 * i / steps);
            } else {
                g.fillOval((int) (x - inc), (int) (y - inc), 20 - 10 * i / steps, 20 - 10 * i / steps);
            }
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

    public void run() {
        double inc = 0.05;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            factor += inc;
            ;
            if (factor > 0.5 || factor < -0.5) {
                inc = -inc;
            }
            repaint();
        }
    }
}
