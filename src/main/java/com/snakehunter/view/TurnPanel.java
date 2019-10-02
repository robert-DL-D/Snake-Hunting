package com.snakehunter.view;

import com.snakehunter.GameContract.GameModel;
import com.snakehunter.GameStage;
import com.snakehunter.model.piece.Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * @author Aspen
 * @date 2019-09-22
 */
public class TurnPanel
        extends JPanel
        implements ActionListener {
    private static final String saveGameString = "Save Game";
    private final String turnNoString = "Turn No: %s/%s";
    private final String stageLabelString = "Stage: ";
    private final String playerturnString = " turn: ";
    private final String humanGuardString = "Guards left: ";
    private static final String PIECES_PARALYSED_TURN = "Pieces Paralysed Turn";
    private static final String PIECE = "Piece";

    private final String[] humanButtons = {"Roll Dice", "Place Guard"};
    private final String[] pieceButtons = {"1", "2", "3", "4"};
    //private final String[] snakeButtons = {"Move Up", "Move Down", "Move Left", "Move Right"};
    private final String[] wideSnakeButtons = {"Up", "Down"};
    private final String[] longSnakeButtons = {"Left", "Right"};

    private final List<JButton> hButtons = new ArrayList<>();
    private LinkedList<JLabel> jLabels = new LinkedList<>();
    private final List<JButton> sButtons = new ArrayList<>();

    private ActionListener listener;

    private GameModel gameModel;
    private JLabel turnNoLabel;
    private JLabel stageNoLabel;
    private JLabel turntakerLabel;
    private JLabel guardLabel;
    private StringBuilder sb = new StringBuilder();

    private JList snakeJList;

    public TurnPanel(ActionListener listener, GameModel gameModel, Color background) {
        this.listener = listener;
        this.gameModel = gameModel;

        setSize(160, 475);

        JButton saveGameButton = new JButton(saveGameString);
        saveGameButton.setPreferredSize(new Dimension(150, 25));
        saveGameButton.addActionListener(this);
        add(saveGameButton);

        stageNoLabel = new JLabel((stageLabelString + gameModel.getGameStage()));
        stageNoLabel.setPreferredSize(new Dimension(150, 20));
        add(stageNoLabel);

        sb.append(String.format("Turn No: "));
        //gameModel.getNumOfTurns()
        turnNoLabel = new JLabel();
        turnNoLabel.setPreferredSize(new Dimension(150, 20));
        turnNoLabel.setText(sb.toString());
        add(turnNoLabel);

        guardLabel = new JLabel();
        guardLabel.setPreferredSize(new Dimension(150, 20));
        guardLabel.setText(humanGuardString + gameModel.getRemainingGuards());
        add(guardLabel);

        turntakerLabel = new JLabel(playerturnString + gameModel.getCurrentPlayer());
        turntakerLabel.setPreferredSize(new Dimension(150, 20));
        add(turntakerLabel);

        JLabel paralyzeLabel = new JLabel(PIECES_PARALYSED_TURN);
        turntakerLabel.setPreferredSize(new Dimension(150, 20));
        add(paralyzeLabel);

        for (int i = 0; i < gameModel.getHumanList().size(); i++) {
            JLabel jLabel = new JLabel(PIECE + " " + (i + 1) + ": " + gameModel.getHumanList().get(i).getParalyzeTurns());
            jLabel.setPreferredSize(new Dimension(150, 15));
            jLabels.add(jLabel);
            add(jLabel);
        }

        snakeJList = new JList();
        snakeJList.setBackground(background);
        snakeJList.setBorder(new LineBorder(Color.BLACK));

    }

    //region functionality

    public void updateTurnNo(int turnNo) {
        sb = new StringBuilder();
        sb.append(String.format(turnNoString, (int) Math.ceil(turnNo / 2.0), (int) Math.ceil(gameModel.getGameStage().getMaxTurns() / 2.0)));
        turnNoLabel.setText(sb.toString());
        String s = "Human's";
        if (gameModel.getCurrentPlayer().isSnake()) {
            s = "Snake's";
            showSnakeButtons();
        } else {
            showHumanButtons();
        }
        turntakerLabel.setText(s + playerturnString + gameModel.getCurrentPlayer().getName());

        // FIXME paralysed turn doesn't update when human rolled a six and land on a snake's head
        for (int i = 0; i < jLabels.size(); i++) {
            jLabels.get(i).setText(PIECE + " " + (i + 1) + ": " + gameModel.getHumanList().get(i).getParalyzeTurns());
        }
    }

    public void updateGuardNo() {
        guardLabel.setText(humanGuardString + gameModel.getRemainingGuards());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(e);
    }

    public void updateStage(GameStage s) {
        stageNoLabel.setText(stageLabelString + s);
    }

    private void showHumanButtons() {
        for (JButton b : sButtons) {
            remove(b);
        }
        for (JButton b : hButtons) {
            remove(b);
        }

        snakeJList.removeAll();
        remove(snakeJList);

        repaint();
        sButtons.clear();
        for (String buttonStr : humanButtons) {
            JButton button = new JButton(buttonStr);
            hButtons.add(button);
            button.setPreferredSize(new Dimension(150, 35));
            button.addActionListener(this);
            add(button);
        }

    }

    void showPieceButtons() {
        for (String pieceButton : pieceButtons) {
            JButton button = new JButton(pieceButton);
            hButtons.add(button);
            button.setPreferredSize(new Dimension(50, 30));
            button.addActionListener(this);
            add(button);
        }
    }

    private void showSnakeButtons() {
        for (JButton b : hButtons) {
            remove(b);
        }
        for (JButton b : sButtons) {
            remove(b);
        }
        repaint();
        hButtons.clear();

        List<Snake> snakeList = gameModel.getSnakeList();
        String[] temp = new String[snakeList.size()];
        for (int i = 0; i < snakeList.size(); i++) {
            Snake snake = snakeList.get(i);
            temp[i] = ("Snake " + (i + 1) + ": " + snake.getPosition() + " " + snake.getConnectedPosition());

        }

        snakeJList.setListData(temp);

        add(snakeJList);

        String moveUp = wideSnakeButtons[0];
        JButton moveUpButton = new JButton(moveUp);
        sButtons.add(moveUpButton);
        moveUpButton.setPreferredSize(new Dimension(140, 30));
        moveUpButton.addActionListener(this);
        add(moveUpButton);

        for (String longSnakeString : longSnakeButtons) {
            JButton longSnakeButton = new JButton(longSnakeString);
            sButtons.add(longSnakeButton);
            longSnakeButton.setPreferredSize(new Dimension(65, 50));
            longSnakeButton.addActionListener(this);
            add(longSnakeButton);
        }

        String moveDown = wideSnakeButtons[1];
        JButton moveDownButton = new JButton(moveDown);
        sButtons.add(moveDownButton);
        moveDownButton.setPreferredSize(new Dimension(140, 30));
        moveDownButton.addActionListener(this);
        add(moveDownButton);

    }

    //endregion

    //region getters

    public int getJListSelectItem() {
        return snakeJList.getSelectedIndex();
    }

    public JLabel getTurnNoLabel() {
        return turnNoLabel;
    }

    public JLabel getStageNoLabel() {
        return stageNoLabel;
    }

    public JLabel getTurntakerLabel() {
        return turntakerLabel;
    }

    public JLabel getGuardLabel() {
        return guardLabel;
    }
    //end region
}
