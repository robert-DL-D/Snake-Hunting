package com.snakehunter.view;

import com.snakehunter.GameContract;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Player;
import com.snakehunter.model.piece.Snake;

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
public class GameViewImpl
        extends JFrame
        implements GameContract.GameView,
                   ActionListener {

    private GameViewListener listener;

    private Board board;
    private Dice dice;
    private SettingPanel settingPanel;

    public GameViewImpl() {
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

        settingPanel = new SettingPanel(this);
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
                listener.onSnakeBuilt(new Snake(head, tail));
            } catch (NumberFormatException nfe) {
                listener.onSnakeBuilt(null);
            }
        }
    }

    @Override
    public void showLadderBuilder() {
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(0, 2, 2, 2));

        JTextField topField = new LimitTextField();
        JTextField baseField = new LimitTextField();

        pane.add(new JLabel("Top position"));
        pane.add(topField);

        pane.add(new JLabel("Base position"));
        pane.add(baseField);

        int option = JOptionPane.showConfirmDialog(this, pane, "Add Ladder", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String topInput = topField.getText();
            String bottomInput = baseField.getText();

            try {
                int top = Integer.parseInt(topInput);
                int bottom = Integer.parseInt(bottomInput);
                listener.onLadderBuilt(new Ladder(top, bottom));
            } catch (NumberFormatException nfe) {
                listener.onLadderBuilt(null);
            }
        }
    }

    @Override
    public void showHowManyPlayers() {
        int numOfPlayers;

        try {
            numOfPlayers = Integer.parseInt(JOptionPane.showInputDialog("How many players? (2 ~ 4)"));
        } catch (NumberFormatException e) {
            if (listener != null) {
                listener.onNumOfPlayersEntered(2);
            }
            return;
        }

        if (numOfPlayers < 2 || numOfPlayers > 4) {
            if (listener != null) {
                listener.onNumOfPlayersEntered(2);
            }
            return;
        }

        numOfPlayers = 2; // FIXME: fixed num of players for now.
        if (listener != null) {
            listener.onNumOfPlayersEntered(numOfPlayers);
        }
    }

    @Override
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Alert", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void hideSettingPanel() {
        settingPanel.setVisible(false);
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
        board.addLadder(ladder);
    }

    @Override
    public void onAddSnakeFailed(String errorMessage) {
        showErrorDialog(errorMessage);
    }

    @Override
    public void onAddLadderFailed(String errorMessage) {
        showErrorDialog(errorMessage);
    }

    @Override
    public void onGuardAdded(int position) {

    }

    @Override
    public void onExceedMaxNumOfGuards() {

    }

    @Override
    public void onPlayersAdded(int numOfPlayers) {
        // TODO: add pieces into board
    }

    @Override
    public void onNextTurn(Player player) {
        dice.setEnabled(true);
        String message = String.format("%1s's turn, roll the dice!", player.getName());
        JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onPlayerMoved(Player player, int destPosition) {
        String message = String.format("%1s move to: %2d", player.getName(), destPosition);
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onPlayerClimbLadder(Player player, int destPosition) {
        String message = String.format("%1s climb a ladder to: %2d", player.getName(), destPosition);
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onPlayerSwallowedBySnake(Player player, int destPosition) {
        String message = String.format("%1s swallowed by a snake and back to: %2d", player.getName(), destPosition);
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onNumOfPlayersEnteredError() {
        showErrorDialog("Please enter a number between 2 ~ 4.");
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
            case "Add Ladder":
            listener.onAddLadderClick();
            break;
        case "Add Players":
            listener.onAddPlayersClick();
            break;
        case "Start":
            listener.onStartClick();
            break;
        default:
            break;
        }
    }

    @Override
    public void setListener(GameViewListener listener) {
        this.listener = listener;
        dice.setListener(listener);
    }

    public interface GameViewListener {
        void onAddSnakeClick();

        void onSnakeBuilt(Snake snake);

        void onAddLadderClick();

        void onLadderBuilt(Ladder ladder);

        void onAddPlayersClick();

        void onStartClick();

        void onDiceClick();

        void onDiceRolled(int num);

        void onNumOfPlayersEntered(int numOfPlayers);
    }
}
