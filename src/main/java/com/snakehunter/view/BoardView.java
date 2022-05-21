package com.snakehunter.view;

import com.snakehunter.model.GameModel;
import com.snakehunter.model.Square;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Piece;
import com.snakehunter.model.piece.Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.List;

import javax.swing.JPanel;

public class BoardView
        extends JPanel
        implements Runnable {
    private static final int X_MARGIN = 20;
    private static final int Y_MARGIN = 20;

    private static final Color snakeTailHereColor = new Color(150, 70, 70);

    private static final Color piece1Color = new Color(136, 94, 209);
    private static final Color piece2Color = new Color(194, 94, 209);
    private static final Color piece3Color = new Color(94, 144, 209);
    private static final Color piece4Color = new Color(25, 181, 158);
    private final Color[] color = {piece1Color, piece2Color, piece3Color, piece4Color};

    private static final Color ladderColor = new Color(90, 245, 90);

    private double factor = 0.2;

    private final GameModel gameModel;

    BoardView(GameModel gameModel) {
        this.gameModel = gameModel;

        setSize(440, 440);
        //setBorder(new LineBorder(Color.BLACK));

        new Thread(this).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Square[][] localSquares = gameModel.getSquares();

        for (int i = 0; i < localSquares.length; i++) {
            for (int j = 0; j < localSquares[0].length; j++) {

                Square square = localSquares[i][9 - j];

                if (square.getSquareNo() % 2 == 0) {
                    g.setColor(new Color(71, 83, 105));
                } else {
                    g.setColor(new Color(49, 62, 87));
                }

                if (!square.getPieceList().isEmpty()) {
                    for (Piece piece : square.getPieceList()) {
                        if (piece instanceof Snake) {
                            if (piece.getPosition() == square.getSquareNo()) {
                                g.setColor(new Color(255, 90, 90));
                                break;
                            }/*else {
                            graphics.setColor(snakeTailHereColor);
                        }*/
                        }
                    }

                    if (square.getLadder() != null) {
                        g.setColor(new Color(81, 140, 97));
                    }
                }

                if (square.isGuarded()) {
                    g.setColor(new Color(50, 190, 200));
                }

                g.fillRect(i * 40 + 20, j * 40 + 20, 40, 40);

            }
        }
        drawSnake(g, gameModel.getSnakeList());

        drawLadder(g, gameModel.getLadderList());

        drawHumans(g, gameModel.getHumanList());

        for (int i = 0; i < localSquares.length; i++) {
            for (int j = 0; j < localSquares[0].length; j++) {
                g.setColor(new Color(255, 255, 255));
                g.drawString(Integer.toString(localSquares[i][9 - j].getSquareNo()), i * 40 + 20, j * 40 + 30);
            }
        }

    }

    private void drawSnake(Graphics g, List<Snake> snakeList) {
        for (int j = 1; j <= snakeList.size(); j++) {
            if (!snakeList.get(j - 1).isSnakeDead()) {
                int headX = getX(snakeList.get(j - 1).getPosition());
                int headY = getY(snakeList.get(j - 1).getPosition());
                int tailX = getX(snakeList.get(j - 1).getConnectedPosition());
                int tailY = getY(snakeList.get(j - 1).getConnectedPosition());

                int steps = (int) Math.sqrt((tailY - headY) * (tailY - headY) + (tailX - headX) * (tailX - headX)) / 150 * 18 + 24;

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
                        g.setColor(new Color(219, 70, 44));
                        odd = false;
                    } else {
                        g.setColor(new Color(166, 49, 28));
                        odd = true;
                    }

                    if (tailX > headX) {
                        g.fillOval((int) (x + inc), (int) (y - inc), 20 - 10 * i / steps, 20 - 10 * i / steps);
                    } else {
                        g.fillOval((int) (x - inc), (int) (y - inc), 20 - 10 * i / steps, 20 - 10 * i / steps);
                    }
                }
            }
        }
    }

    private void drawLadder(Graphics g, List<Ladder> ladderList) {
        for (Ladder ladder : ladderList) {

            int bottomX = getX(ladder.getPosition()) + 10;
            int bottomY = getY(ladder.getPosition()) + 10;
            int topX = getX(ladder.getConnectedPosition()) + 10;
            int topY = getY(ladder.getConnectedPosition()) + 10;

            g.setColor(ladderColor);

            double dx = topX - bottomX;
            double dy = topY - bottomY;

            double rawdX = dx;
            double rawdY = dy;

            double dist = Math.sqrt(dx * dx + dy * dy);

            dx /= dist;
            dy /= dist;

            int ladderWidth = 5;
            int rungMin = 3;
            int rungMax = 40;
            int rungSpacing = 10;

            int rungNo = Math.min(rungMax, Math.max((int) dist / rungSpacing, rungMin));

            int firstxpoint = 0;
            int firstypoint = 0;
            int thirdxpoint = 0;
            int thirdypoint = 0;
            int secondxpoint = 0;
            int secondypoint = 0;
            int fourthxpoint = 0;
            int fourthypoint = 0;

            for (int i = 0; i < rungNo; i++) {
                double xpoint = bottomX + rawdX / rungNo * i;

                double ypoint = bottomY + rawdY / rungNo * i;

                if (i == 0) {
                    firstxpoint = (int) (xpoint + ladderWidth * dy);
                    firstypoint = (int) (ypoint - ladderWidth * dx);
                    thirdxpoint = (int) (xpoint - ladderWidth * dy);
                    thirdypoint = (int) (ypoint + ladderWidth * dx);
                } else if (i == rungNo - 1) {
                    secondxpoint = (int) (xpoint + ladderWidth * dy);
                    secondypoint = (int) (ypoint - ladderWidth * dx);
                    fourthxpoint = (int) (xpoint - ladderWidth * dy);
                    fourthypoint = (int) (ypoint + ladderWidth * dx);

                } else {
                    g.drawLine((int) (xpoint + ladderWidth * dy),
                            (int) (ypoint - ladderWidth * dx),
                            (int) (xpoint - ladderWidth * dy),
                            (int) (ypoint + ladderWidth * dx));
                }
            }

            g.drawLine(firstxpoint, firstypoint, secondxpoint, secondypoint);
            g.drawLine(thirdxpoint, thirdypoint, fourthxpoint, fourthypoint);

            Polygon p = new Polygon();
            p.addPoint(firstxpoint, firstypoint);
            p.addPoint(secondxpoint, secondypoint);
            p.addPoint(fourthxpoint, fourthypoint);
            p.addPoint(thirdxpoint, thirdypoint);

            g.setColor(new Color(ladderColor.getRed(), ladderColor.getGreen(), ladderColor.getBlue(), 64));
            g.drawPolygon(p);
            g.fillPolygon(p);
        }
    }

    private void drawHumans(Graphics g, List<Human> humanList) {

        for (int i = 1; i <= humanList.size(); i++) {
            if (!humanList.get(i - 1).isDead()) {

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

                if (humanList.get(i - 1).getParalyzeTurns() > 0) {
                    if (humanList.get(i - 1).getParalyzeTurns() >= 3) {
                        g.setColor(new Color(227, 44, 11));
                    } else if (humanList.get(i - 1).getParalyzeTurns() >= 2) {
                        g.setColor(new Color(227, 112, 11));
                    } else {
                        g.setColor(new Color(227, 148, 11));
                    }
                    g.fillOval(getX(humanPosition) - xOffset, getY(humanPosition) - yOffset, 20, 20);
                }

                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(i), getX(humanPosition) - (xOffset - 5), getY(humanPosition) + (-yOffset + 15));
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
                Thread.sleep(50);
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
