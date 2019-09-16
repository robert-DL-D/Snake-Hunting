package com.snakehunter.view;

import com.snakehunter.GameContract;
import com.snakehunter.GameContract.ViewEventListener;
import com.snakehunter.Main;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;
import com.snakehunter.model.piece.Snake;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

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

    private ViewEventListener listener;

    private BoardView boardView;
    private DiceView diceView;
    private SettingPanel settingPanel;

    public GameViewImpl() {
        super("Snakes n Ladders!");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(645, 520);

        Container contentPane = getContentPane();

        boardView = new BoardView();
        boardView.setLocation(0, 0);
        contentPane.add(boardView);

        diceView = new DiceView();
        diceView.setLocation(500, 400);
        contentPane.add(diceView);

        settingPanel = new SettingPanel(this);
        settingPanel.setLocation(450, 20);
        contentPane.add(settingPanel);

        setVisible(true);
    }

    //region interaction
    @Override
    public void rollTheDice() {
        if (Main.isDebugMode()) {
            int num = 0;
            try {
                num = Integer.parseInt(JOptionPane.showInputDialog("Enter 0 to throw dice. Enter 1 - 6 for Testing."));
            } catch (NumberFormatException e) {
                showErrorDialog("PLease enters a valid number!");
                return;
            }

            if (num == 0) {
                diceView.roll();
            } else {
                listener.onDiceRolled(num);
            }
        } else {
            diceView.roll();
        }
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
                listener.onLadderBuilt(new Ladder(bottom, top));
            } catch (NumberFormatException nfe) {
                listener.onLadderBuilt(null);
            }
        }
    }

    @Override
    public void showHumanBuilder() {
        int numOfHumans;

        try {
            numOfHumans = Integer.parseInt(JOptionPane.showInputDialog("How many players? (2 ~ 4)"));
        } catch (NumberFormatException e) {
            if (listener != null) {
                listener.onNumOfHumansEntered(-1);
            }
            return;
        }

        if (listener != null) {
            listener.onNumOfHumansEntered(numOfHumans);
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
        boardView.addSnake(snake);
    }

    @Override
    public void onLadderAdded(Ladder ladder) {
        boardView.addLadder(ladder);
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
        showErrorDialog("Cannot place more than 3 guards.");
    }

    @Override
    public void onHumansAdded(Map<Integer, Human> humanMap) {
        boardView.addHumans(humanMap);
    }

    @Override
    public void onNextTurn(Human human) {
        diceView.setEnabled(true);
        String message = String.format("%1s's turn, roll the dice!", human.getName());
        JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onPlayerMoved(Human human, int destPosition) {
        String message = String.format("%1s move to: %2d", human.getName(), destPosition);
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onPlayerClimbLadder(Human human, int destPosition) {
        String message = String.format("%1s climb a ladder to: %2d", human.getName(), destPosition);
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onPlayerSwallowedBySnake(Human human, int destPosition) {
        String message = String.format("%1s swallowed by a snake and back to: %2d", human.getName(), destPosition);
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onNumOfHumansEnteredError() {
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
        case "Add Humans":
            listener.onAddHumansClick();
            break;
        case "Start":
            listener.onStartClick();
            break;
        default:
            break;
        }
    }

    @Override
    public void setOnViewEventListener(ViewEventListener listener) {
        this.listener = listener;
        diceView.setOnViewEventListener(listener);
    }

}
