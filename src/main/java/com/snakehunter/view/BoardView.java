package com.snakehunter.view;

import com.snakehunter.GameContract.GameModel;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Snake;
import com.snakehunter.model.Square;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class BoardView
        extends JPanel
        implements Runnable {
    private static final int X_MARGIN = 20;
    private static final int Y_MARGIN = 20;

    private static final Color boardColor1 = new Color(71, 83, 105);
    private static final Color boardColor2 = new Color(49, 62, 87);

    private static final Color snakeHeadHereColor = new Color(255, 90, 90);

    private static final Color ladderBaseHereColor = new Color(81, 140, 97);

    private static final Color piece1Color = new Color(124, 100, 176);
    private static final Color piece2Color = new Color(168, 109, 176);
    private static final Color piece3Color = new Color(227, 217, 107);
    private static final Color piece4Color = new Color(227, 157, 82);

    private static final Color boardNumberColor = new Color(255, 255, 255);

    private static final Color snakeColor1 = new Color(219, 70, 44);
    private static final Color snakeColor2 = new Color(166, 49, 28);

    private static final Color ladderColor = new Color(90, 245, 90);

    private double factor = 0.2;

    private GameModel gameModel;

    private List<Snake> snakeList;
    private List<Ladder> ladderList;

    public BoardView(GameModel gameModel) {
        this.gameModel = gameModel;

        setSize(440, 440);
        snakeList = new ArrayList<>();
        ladderList = new ArrayList<>();

        new Thread(this).start();
    }

    public void addSnake(Snake snake) {
        // Runnable will repaint every 1 sec, no need to repaint here.
        snakeList.add(snake);
    }

    public void addLadder(Ladder ladder) {
        ladderList.add(ladder);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                if ((i + j) % 2 == 0) {
//                    graphics.setColor(Color.YELLOW);
//                } else {
//                    graphics.setColor(Color.ORANGE);
//                }
//                graphics.fillRect(20 + 40 * i, 20 + 40 * j, 40, 40);
//            }
//        }

        Square[][] localSquares = gameModel.getSquares();

        for(int i=0; i < localSquares.length ; i++){
            for (int j=0; j < localSquares[0].length ; j++){

                //System.out.println(i + " " + j);
                if (localSquares[i][9 - j].getSquareNo() % 2 == 0){
                    graphics.setColor(boardColor1);
                } else {
                    graphics.setColor(boardColor2);
                }

                if (localSquares[i][9 - j].getPieceList().size() != 0){
                    if (localSquares[i][9 - j].getSnake()!= null) {
                        graphics.setColor(snakeHeadHereColor);
                    } else if (localSquares[i][9 - j].getLadder() != null){
                        graphics.setColor(ladderBaseHereColor);
                    }
                }

                graphics.fillRect(i * 40 + 20, j * 40 + 20, 40, 40);
                graphics.setColor(boardNumberColor);
                graphics.drawString(Integer.toString(localSquares[i][9-j].getSquareNo()), i * 40 + 20, j * 40 + 40);
            }
        }


//        for (int i = 0; i < 100; i++) {
//            graphics.drawString("" + (i + 1), getX(i + 1), getY(i + 1) + 20);
//        }

        for (Snake snake : snakeList) {
            drawSnake(graphics, snake);
        }

        for (Ladder ladder : ladderList) {
            drawLadder(graphics, ladder);
        }

        drawHumans(graphics, gameModel.getHumanList());
    }

    private void drawSnake(Graphics g, Snake snake) {

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
                g.setColor(snakeColor1);
                odd = false;
            } else {
                g.setColor(snakeColor2);
                odd = true;
            }
            if (tailX > headX) {
                g.fillOval((int) (x + inc), (int) (y - inc), 20 - 10 * i / steps, 20 - 10 * i / steps);
            } else {
                g.fillOval((int) (x - inc), (int) (y - inc), 20 - 10 * i / steps, 20 - 10 * i / steps);
            }
        }
    }

    private void drawLadder(Graphics g, Ladder ladder) {

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

        g.setColor(ladderColor);
        g.drawLine((topX - xinc), (topY + yinc), (bottomX - xinc), (buttomY + yinc));
        g.drawLine((topX - xinc) - 1, (topY + yinc), (bottomX - xinc) - 1, (buttomY + yinc));
        g.drawLine((topX - xinc), (topY + yinc) - 1, (bottomX - xinc), (buttomY + yinc) - 1);

        g.drawLine((topX + xinc), (topY - yinc), (bottomX + xinc), (buttomY - yinc));
        g.drawLine((topX + xinc) - 1, (topY - yinc), (bottomX + xinc) - 1, (buttomY - yinc));
        g.drawLine((topX + xinc), (topY - yinc) - 1, (bottomX + xinc), (buttomY - yinc) - 1);

       // g.setColor(Color.yellow);
        int[] x = {(topX), (bottomX),(bottomX) - 10, (topX) - 10};
        int[] y = {(topY), (buttomY), (buttomY) - 10, (topY) - 10};
       // g.drawPolygon(x, y, 4);

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

    private void drawHumans(Graphics g, List<Human> humanList) {
        Color[] color = new Color[] {piece1Color, piece2Color, piece3Color, piece4Color};

        for (int i = 1; i <= humanList.size(); i++) {
            g.setColor(color[i - 1]);
            int humanPosition = humanList.get(i - 1).getPosition();
            int xOffset = 10;
            int yOffset = 10;

            if (i % 2 == 0) {
                xOffset = -xOffset;
            }

            if (i > 2) {
                yOffset = -yOffset;
            }

            g.fillOval(getX(humanPosition) - xOffset, getY(humanPosition) - yOffset, 20, 20);

            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(i), getX(humanPosition) - (xOffset - 5),
                         getY(humanPosition) + (-yOffset + 15));
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
