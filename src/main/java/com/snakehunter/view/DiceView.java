package com.snakehunter.view;

import com.snakehunter.Main;
import com.snakehunter.controller.GameController;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

public class DiceView
        extends ClickablePanel {

    private static final int DICE_LENGTH = 45;
    private static final int DOT_LENGTH = 5;

    private static final int DICE_NUM_MIN = 1;
    private static final int DICE_NUM_MAX = 6;

    private GameController listener;

    private int lastNum = 1;
    private int storedValue;
    private boolean usedStoredValue = true;

    DiceView() {
        setSize(DICE_LENGTH, DICE_LENGTH);
        addMouseListener(this);
    }

    void setOnViewEventListener(GameController listener) {
        this.listener = listener;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        listener.onDiceClick();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fill3DRect(0, 0, DICE_LENGTH, DICE_LENGTH, false);

        g.setColor(Color.BLACK);

        if (lastNum == 1) {
            g.fillOval(20, 20, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 2) {
            g.fillOval(10, 20, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(30, 20, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 3) {
            g.fillOval(20, 10, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 4) {
            g.fillOval(10, 10, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(30, 10, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 5) {
            g.fillOval(10, 10, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(30, 10, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(20, 20, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        } else if (lastNum == 6) {
            g.fillOval(10, 10, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(30, 10, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(10, 20, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(30, 20, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(10, 30, DOT_LENGTH, DOT_LENGTH);
            g.fillOval(30, 30, DOT_LENGTH, DOT_LENGTH);
        }
    }

    synchronized void roll() {

        if (isEnabled()) {
            setEnabled(false);
            lastNum = 0;

            new Thread(() -> {
                if (Main.isDebugMode()) {
                    String s = JOptionPane.showInputDialog("DEBUG: input dice number or 0 to roll dice");
                    lastNum = Integer.parseInt(s);
                }

                try {

                    if (lastNum == 0) {
                        int num;

                        if (usedStoredValue) {
                            // fake dice animation
                            for (int i = 1; i <= 20; i++) {
                                do {
                                    num = ThreadLocalRandom.current().nextInt(DICE_NUM_MIN, DICE_NUM_MAX + 1);
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
                        } else {
                            lastNum = storedValue;
                        }

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Invalid dice number");
                }

                storedValue = lastNum;
            }).start();
        }

        setEnabled(true);
    }

    void moveSelectedPiece(int selectedPiece) {
        if (listener.onDiceRolled(selectedPiece - 1, lastNum)) {
            usedStoredValue = true;

            setEnabled(true);
        }
    }

}