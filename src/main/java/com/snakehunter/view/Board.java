package com.snakehunter.view;

import com.snakehunter.model.piece.Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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

    private List<Snake> snakeList;

    public Board() {
        setSize(440, 440);
        snakeList = new ArrayList<>();

        new Thread(this).start();
    }

    public void addSnake(Snake snake) {
        // Runnable will repaint every 1 sec, no need to repaint here.
        snakeList.add(snake);
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

        for (Snake snake : snakeList) {
            drawSnake(graphics, snake);
        }
    }

    public void drawSnake(Graphics g, Snake snake) {
        int headX = getX(snake.getPosition());
        int headY = getY(snake.getPosition());
        int tailX = getX(snake.getConnectedPosition());
        int tailY = getY(snake.getConnectedPosition());

        int steps =
                (int) Math.sqrt((tailY - headY) * (tailY - headY) + (tailX - headX) * (tailX - headX)) / 150 * 18 + 24;

        double xstep = (double) (tailX - headX) / (steps + 1);
        double ystep = (double) (tailY - headY) / (steps + 1);

        double inc;
        double x = headX, y = headY;

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
            if (tailX > headX) {
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

            if (factor > 0.5 || factor < -0.5) {
                inc = -inc;
            }
            repaint();
        }
    }
}
