package com.snakehunter.view;

import com.snakehunter.GameContract;
import com.snakehunter.model.GameModel.GameModelListener;
import com.snakehunter.model.Ladder;
import com.snakehunter.model.Snake;
import com.snakehunter.model.exception.NumberRangeException;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class GameView
        extends JFrame
        implements GameContract.GameView,
                   ActionListener,
                   GameModelListener {

    private GameViewListener listener;

    private Board board;
    private Dice dice;

    public GameView() {
        super("Snakes n Ladders!");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(645, 520);

        Container contentPane = getContentPane();

        board = new Board();
        board.setLocation(0, 0);
        contentPane.add(board);

        dice = new Dice();
        dice.setLocation(500, 400);
        contentPane.add(dice);

        SettingPanel settingPanel = new SettingPanel(this);
        settingPanel.setLocation(450, 20);
        contentPane.add(settingPanel);

        setVisible(true);
    }

    //region interaction
    @Override
    public void rollTheDice() {
        dice.roll();
    }

    @Override
    public void showSnakeBuilder() {
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(0, 2, 2, 2));

        JTextField headField = new LimitTextField();
        JTextField tailField = new LimitTextField();

        pane.add(new JLabel("Head position"));
        pane.add(headField);

        pane.add(new JLabel("Tail position"));
        pane.add(tailField);

        int option = JOptionPane.showConfirmDialog(this, pane, "Add Snake", JOptionPane.OK_CANCEL_OPTION,
                                                   JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String headInput = headField.getText();
            String tailInput = tailField.getText();

            try {
                int head = Integer.parseInt(headInput);
                int tail = Integer.parseInt(tailInput);
                listener.onSnakeBuilt(head, tail);
            } catch (NumberFormatException nfe) {
                listener.onSnakeBuilt(-1, -1);
            }
        }
    }

    @Override
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

    @Override
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Alert", JOptionPane.ERROR_MESSAGE);
    }
    //endregion

    //region GameModel interaction
    @Override
    public void onSnakeAdded(Snake snake) {
        board.addSnake(snake);
    }

    @Override
    public void onLadderAdded(Ladder ladder) {
        // TODO: add ladder into board
    }

    @Override
    public void onAddFailed(String errorMessage) {
        showErrorDialog(errorMessage);
    }

    @Override
    public void onPlayersAdded(int numOfPlayers) {
        // TODO: add pieces into board
    }
    //endregion

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener == null) {
            return;
        }

        switch (e.getActionCommand()) {
        case "Add Snake":
            listener.onAddSnakeClick();
            break;
        case "Add com.snakehunter.model.Ladder":
            listener.onAddLadderClick();
            break;
        case "Start":
            listener.onStartClick();
            break;
        default:
            break;
        }
    }

    public void setListener(GameViewListener listener) {
        this.listener = listener;
        dice.setListener(listener);
    }

    public interface GameViewListener {
        void onAddSnakeClick();

        void onSnakeBuilt(int head, int tail);

        void onAddLadderClick();

        void onStartClick();

        void onDiceClick();

        void onDiceRolled(int num);

        void onNumOfPlayersEntered(int numOfPlayers);
    }
}
