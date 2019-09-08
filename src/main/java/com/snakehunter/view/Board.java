package com.snakehunter.view;

import com.snakehunter.model.piece.Ladder;
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
    private List<Ladder> ladderList;

    /* FIXME drawPlayers requires Players in an Array
    private List<Ladder> playerList;
    private int numOfPlayers;
     */

    public Board() {
        setSize(440, 440);
        snakeList = new ArrayList<>();
        ladderList = new ArrayList<>();
        //playerList = new ArrayList<>();

        new Thread(this).start();
    }

    public void addSnake(Snake snake) {
        // Runnable will repaint every 1 sec, no need to repaint here.
        snakeList.add(snake);
    }

    public void addLadder(Ladder ladder) {
        ladderList.add(ladder);
    }

    public void addPlayer(int numOfPlayers) {
        //this.numOfPlayers = numOfPlayers;
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

        for (Ladder ladder : ladderList) {
            drawLadder(graphics, ladder);
        }

        //drawPieces(graphics);
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

    public void drawLadder(Graphics g, Ladder ladder) {

        int bottomX = getX(ladder.getPosition());
        int buttomY = getY(ladder.getPosition());
        int topX = getX(ladder.getConnectedPosition());
        int topY = getY(ladder.getConnectedPosition());

        int steps = (int) Math.sqrt((buttomY - topY) * (buttomY - topY) + (bottomX - topX) * (bottomX - topX)) / 25 + 1;

        int xinc = 5;
        if (topX > bottomX) {
            xinc = -xinc;
        }
        int yinc = 5;
        if (topY > buttomY) {
            yinc = -yinc;
        }

        g.drawLine((topX - xinc), (topY + yinc), (bottomX - xinc), (buttomY + yinc));
        g.drawLine((topX - xinc) - 1, (topY + yinc), (bottomX - xinc) - 1, (buttomY + yinc));
        g.drawLine((topX - xinc), (topY + yinc) - 1, (bottomX - xinc), (buttomY + yinc) - 1);

        g.drawLine((topX + xinc), (topY - yinc), (bottomX + xinc), (buttomY - yinc));
        g.drawLine((topX + xinc) - 1, (topY - yinc), (bottomX + xinc) - 1, (buttomY - yinc));
        g.drawLine((topX + xinc), (topY - yinc) - 1, (bottomX + xinc), (buttomY - yinc) - 1);

        double xstep = (bottomX - topX) / (steps + 1);
        double ystep = (buttomY - topY) / (steps + 1);
        for (int i = 0; i < steps; i++) {
            topX += xstep;
            topY += ystep;
            g.drawLine((topX + xinc), (topY - yinc), (topX - xinc), (topY + yinc));
            g.drawLine((topX + xinc) - 1, (topY - yinc), (topX - xinc) - 1, (topY + yinc));
            g.drawLine((topX + xinc), (topY - yinc) - 1, (topX - xinc), (topY + yinc) - 1);
        }

    }

    // FIXME drawPlayers needs x and y coords
   /* public void drawPlayers(Graphics g) {
        if (pieces.length > 0) {
            g.setColor(Color.WHITE);
            g.fillOval((int) getX(pieces[0]) - 10, getY(pieces[0]) - 10, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString("1", (int) getX(pieces[0]) - 5, getY(pieces[0]) + 5);
        }
        if (pieces.length > 1) {
            g.setColor(Color.RED);
            g.fillOval((int) getX(pieces[1]) + 10, getY(pieces[1]) - 10, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString("2", (int) getX(pieces[1]) + 15, getY(pieces[1]) + 5);
        }
        if (pieces.length > 2) {
            g.setColor(Color.GRAY);
            g.fillOval((int) getX(pieces[2]) - 10, getY(pieces[2]) + 10, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString("3", (int) getX(pieces[2]) - 5, getY(pieces[2]) + 25);
        }
        if (pieces.length > 3) {
            g.setColor(Color.CYAN);
            g.fillOval((int) getX(pieces[3]) + 10, getY(pieces[3]) + 10, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString("4", (int) getX(pieces[3]) + 15, getY(pieces[3]) + 25);
        }
    }*/

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
