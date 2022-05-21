package com.snakehunter.view;

import com.snakehunter.model.GameModel;
import com.snakehunter.model.piece.Player;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverPanel
        extends JPanel
        implements ActionListener {

    private final ActionListener listener;
    private final JLabel winnerLabel;
    private static final String winnerText = "%s Wins!";

    GameOverPanel(ActionListener listener, GameModel gameModel) {
        this.listener = listener;

        setSize(150, 400);

        JLabel gameOverText = new JLabel("Game Over!");
        gameOverText.setPreferredSize(new Dimension(150, 50));
        add(gameOverText);

        winnerLabel = new JLabel(winnerText);
        winnerLabel.setPreferredSize(new Dimension(150, 50));
        add(winnerLabel);

        JButton restartButton = new JButton("Play again");
        restartButton.setPreferredSize(new Dimension(150, 50));
        restartButton.addActionListener(this);
        add(restartButton);
    }

    void updateWinner(Player winner) {
        winnerLabel.setText(String.format(winnerText, "'" + winner.getName() + "'"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(e);
    }
}
