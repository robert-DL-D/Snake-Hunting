package com.snakehunter.view;

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
        implements ActionListener {

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

    public void rollTheDice() {
        dice.roll();
    }

    public void showSnakeBuilder() {
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(0, 2, 2, 2));

        JTextField daysField = new JTextField(5);
        JTextField assignmentField = new JTextField(5);

        pane.add(new JLabel("Head position"));
        pane.add(daysField);

        pane.add(new JLabel("Tail position"));
        pane.add(assignmentField);

        int option = JOptionPane.showConfirmDialog(this, pane, "Please fill all the fields", JOptionPane.YES_NO_OPTION,
                                                   JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {

            String daysInput = daysField.getText();
            String assignmentsInput = assignmentField.getText();

            try {
                int head = Integer.parseInt(daysInput);
                int tail = Integer.parseInt(assignmentsInput);
                listener.onSnakeBuilt(head, tail);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
    }

    public void addSnake(int head, int tail){
        board.addSnake(head, tail);
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

        switch (e.getActionCommand()) {
        case "Add Snake":
            listener.onAddSnakeClick();
            break;
        case "Add Ladder":
            listener.onAddLadderClick();
            break;
        case "Start":
            listener.onStartClick();
            break;
        default:
            break;
        }

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
