package com.snakehunter.view;

import com.snakehunter.model.exception.NumberRangeException;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class GameView
        extends JFrame
        implements ActionListener,
                   Runnable {

    private GameViewListener listener;

    private Board board;
    private Dice dice;

    private int factor = 1;

    public GameView() {
        super("Snakes n Ladders!");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(645, 520);
        setVisible(true);

        Container contentPane = getContentPane();

        board = new Board();
        board.setSize(440, 440);
        contentPane.add(board);

        dice = new Dice();
        dice.setLocation(500, 400);
        dice.setSize(45, 45);
        contentPane.add(dice);

//        new Thread(this).start();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            factor = -factor;
            repaint();
        }
    }

    public void rollTheDice() {
        // FIXME
        new Thread(dice).start();
    }

    public void showHowManyPlayers() throws NumberRangeException {
        int numOfPlayers;

        try {
            numOfPlayers = Integer.parseInt(JOptionPane.showInputDialog("How many players? (2 ~ 4)"));
        } catch (NumberFormatException e) {
            throw new NumberRangeException();
        }

        if (numOfPlayers < 2 || numOfPlayers > 4) {
            throw new NumberRangeException();
        }

        numOfPlayers = 2; // FIXME: fixed num of players for now.
        if (listener != null) {
            listener.onNumOfPlayersEntered(2);
        }
    }

    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Alert", JOptionPane.ERROR_MESSAGE);
    }

    public void setListener(GameViewListener listener) {
        this.listener = listener;
        dice.setListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener == null) {
            return;
        }
    }

    public interface GameViewListener {
        void onStartClick();

        void onDiceClick();

        void onNumOfPlayersEntered(int numOfPlayers);
    }
}
