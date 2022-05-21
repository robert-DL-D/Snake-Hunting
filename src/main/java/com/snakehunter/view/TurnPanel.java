package com.snakehunter.view;

import com.snakehunter.GameStage;
import com.snakehunter.controller.GameController;
import com.snakehunter.model.GameModel;
import com.snakehunter.model.piece.Human;
import com.snakehunter.model.piece.Ladder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * @author Aspen
 * @date 2019-09-22
 */
public class TurnPanel
        extends JPanel
        implements ActionListener {

    private static final String TURN_NO = "Turn No: ";
    private static final String saveGameString = "Save Game";
    private static final String stageLabelString = "Stage: ";
    private static final String playerTurnString = " turn: ";
    private static final String humanGuardString = "Guards left: ";
    private static final String PIECES_PARALYSED_TURN = "Pieces Paralysed Turn";
    private static final String SHOW_VALID_MOVES = "Show Valid Moves";
    private final String[] humanButtons = {"Place Guard", "Check Ladder"};
    private final String[] pieceButtons = {"1", "2", "3", "4"};
    private final String[] wideSnakeButtons = {"Up", "Down"};
    private final String[] longSnakeButtons = {"Left", "Right"};
    private static final String moveKnightButton = "Move Human";

    private final Collection<JButton> hButtons = new ArrayList<>();
    private final Collection<JButton> pButtons = new ArrayList<>();
    private final AbstractSequentialList<JLabel> piecesParalyzedLabels = new LinkedList<>();
    private final Collection<JButton> sButtons = new ArrayList<>();
    private final Collection<JButton> humanKnightMoveButtons = new LinkedList<>();
    private final JList<String> validMovesList = new JList<>();

    private final JLabel turnNoLabel;
    private final JLabel stageNoLabel;
    private final JLabel turntakerLabel;
    private final JLabel guardLabel;
    private final JLabel knightLabel = new JLabel("Choose a human to move:");
    private final JList<String> snakeJList;
    private final JList<String> humanJList;
    private final JLabel paralyzeLabel;
    private final JButton moveKnightBut = new JButton(moveKnightButton);
    //private JList<String> validMovesList;

    private final DiceView diceView;
    private final ActionListener listener;
    private final GameModel gameModel;
    private final Color background;
    private StringBuilder sb = new StringBuilder();

    TurnPanel(ActionListener listener, GameModel gameModel, Color background) {
        this.listener = listener;
        this.gameModel = gameModel;
        this.background = background;

        setSize(160, 520);

        JButton saveGameButton = new JButton(saveGameString);
        saveGameButton.setPreferredSize(new Dimension(150, 25));
        saveGameButton.addActionListener(this);
        add(saveGameButton);

        stageNoLabel = new JLabel((stageLabelString + gameModel.getGameStage()));
        stageNoLabel.setPreferredSize(new Dimension(150, 20));
        add(stageNoLabel);

        sb.append(TURN_NO);
        turnNoLabel = new JLabel();
        turnNoLabel.setPreferredSize(new Dimension(150, 20));
        turnNoLabel.setText(sb.toString());
        add(turnNoLabel);

        guardLabel = new JLabel();
        guardLabel.setPreferredSize(new Dimension(150, 20));
        guardLabel.setText(humanGuardString + gameModel.getRemainingGuards());
        add(guardLabel);

        moveKnightBut.setPreferredSize(new Dimension(150, 25));
        moveKnightBut.addActionListener(this);

        paralyzeLabel = new JLabel(PIECES_PARALYSED_TURN);
        paralyzeLabel.setPreferredSize(new Dimension(150, 20));
        add(paralyzeLabel);

        for (int i = 0; i < gameModel.getHumanList().size(); i++) {
            JLabel jLabel = new JLabel(getParalyzedText(i));
            jLabel.setPreferredSize(new Dimension(30, 10));
            piecesParalyzedLabels.add(jLabel);
            add(jLabel);
        }

        turntakerLabel = new JLabel(playerTurnString + gameModel.getCurrentPlayer());
        turntakerLabel.setPreferredSize(new Dimension(150, 20));
        add(turntakerLabel);

        snakeJList = new JList<>();
        snakeJList.setBackground(background);
        snakeJList.setBorder(new LineBorder(Color.BLACK));

        humanJList = new JList<>();
        humanJList.setBackground(background);
        humanJList.setBorder(new LineBorder(Color.BLACK));

        /*validMovesList = new JList<>();
        validMovesList.setBackground(background);
        validMovesList.setBorder(new LineBorder(Color.BLACK));*/

        diceView = new DiceView();
        diceView.setPreferredSize(new Dimension(120, 50));
        diceView.setLocation(100, 0);
        add(diceView);

        addPieceButtons();
    }

    //region functionality

    void updateTurnNo(int turnNo) {
        sb = new StringBuilder();

        String turnNoString = "Turn No: %s/%s";
        sb.append(String.format(turnNoString, (int) Math.ceil(turnNo / 2.0),
                (int) Math.ceil(gameModel.getGameStage().getMaxTurns() / 2.0)));

        turnNoLabel.setText(sb.toString());

        String s = "Human's";
        if (gameModel.getCurrentPlayer().isSnake()) {
            s = "Snake's";
            showSnakeButtons();
        } else {
            showHumanButtons();
        }
        turntakerLabel.setText(s + playerTurnString + gameModel.getCurrentPlayer().getName().toUpperCase());

        updateParalyzedTurn();
    }

    void updateGuardNo() {
        if (gameModel.getGameStage() == GameStage.SECOND) {
            guardLabel.setText(humanGuardString + gameModel.getRemainingGuards());
        } else {
            remove(guardLabel);
        }
    }

    public void updateParalyzedTurn() {
        if (gameModel.getGameStage() == GameStage.SECOND) {
            for (int i = 0; i < piecesParalyzedLabels.size(); i++) {
                piecesParalyzedLabels.get(i).setText(getParalyzedText(i));
            }
        } else if (gameModel.getGameStage() == GameStage.FINAL) {
            remove(paralyzeLabel);
            for (JLabel piecesParalyzedLabel : piecesParalyzedLabels) {
                remove(piecesParalyzedLabel);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(e);
    }

    void updateStage(GameStage gameStage) {
        stageNoLabel.setText(stageLabelString + gameStage);
    }

    private void showHumanButtons() {
        for (JButton jButton : sButtons) {
            remove(jButton);
        }
        for (JButton jButton : hButtons) {
            remove(jButton);
        }

        snakeJList.removeAll();
        remove(snakeJList);

        repaint();
        sButtons.clear();

        if (gameModel.getGameStage() == GameStage.SECOND) {

            diceView.setVisible(true);

            for (String buttonStr : humanButtons) {
                JButton button = new JButton(buttonStr);
                hButtons.add(button);
                button.setPreferredSize(new Dimension(150, 25));
                button.addActionListener(this);
                add(button);
            }

            for (JButton jButton : pButtons) {
                jButton.setVisible(true);
                jButton.setEnabled(false);
            }

        } else {
            diceView.setVisible(false);

            for (JButton pButton : pButtons) {
                pButton.setVisible(false);
            }

            showHumanKnightJList();

            JButton button = new JButton(SHOW_VALID_MOVES);
            hButtons.add(button);
            button.setPreferredSize(new Dimension(150, 25));
            button.addActionListener(this);
            add(button);

            //add(validMovesList);

        }

    }

    private void hideKnightUI() {

        humanJList.setEnabled(false);
        humanJList.setVisible(false);
        moveKnightBut.setEnabled(false);
        moveKnightBut.setVisible(false);
        validMovesList.setEnabled(false);
        validMovesList.setVisible(false);
        knightLabel.setEnabled(false);
        knightLabel.setVisible(false);
    }

    private void addPieceButtons() {
        for (String pieceString : pieceButtons) {
            JButton button = new JButton(pieceString);
            button.setPreferredSize(new Dimension(50, 30));
            button.addActionListener(this);

            pButtons.add(button);

            add(button);
            button.setEnabled(false);
        }
    }

    void enablePieceButtons() {
        for (JButton button : pButtons) {
            button.setEnabled(true);
        }
    }

    public void disablePieceButtons() {
        for (JButton jButton : pButtons) {
            jButton.setEnabled(false);
        }
    }

    public void hidePieceButtons() {
        for (JButton jButton : pButtons) {
            jButton.setVisible(false);
        }
    }

    public void hideDice() {
        diceView.setVisible(false);
    }

    private void showSnakeButtons() {

        for (JButton jButton : hButtons) {
            remove(jButton);
        }
        for (JButton jButton : sButtons) {
            remove(jButton);
        }

        hideKnightUI();

        hidePieceButtons();
        repaint();
        hButtons.clear();
        diceView.setVisible(false);

        String[] snakePosArray = new String[gameModel.getSnakeList().size()];
        for (int i = 0; i < gameModel.getSnakeList().size(); i++) {
            snakePosArray[i] = ("Snake " + (i + 1) + ": " + gameModel.getSnakeList().get(i).getPosition() + " " + gameModel.getSnakeList().get(i).getConnectedPosition());
        }

        snakeJList.setListData(snakePosArray);
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

    private void showHumanKnightJList() {
        humanJList.setEnabled(true);
        humanJList.setVisible(true);
        knightLabel.setEnabled(true);
        knightLabel.setVisible(true);
        add(knightLabel);
        String[] humanPieces = new String[gameModel.getHumanList().size()];
        for (int i = 0; i < humanPieces.length; i++) {
            if (gameModel.getHumanList().get(i).getPosition() != 100) {
                humanPieces[i] = ("Human " + (i + 1) + " @ pos " + gameModel.getHumanList().get(i).getPosition());
            }
        }
        humanJList.setListData(humanPieces);
        add(humanJList);
    }

    void showClimbedLadder() {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < gameModel.getHumanList().size(); i++) {
            Human human = gameModel.getHumanList().get(i);

            stringBuilder.append("Piece ").append((i + 1)).append(" @ ").append(human.getPosition()).append(":");

            for (Ladder ladder : human.getLadderClimbedList()) {
                stringBuilder.append(ladder.getPosition()).append("|");
            }

            stringBuilder.append(System.lineSeparator());
        }

        JOptionPane.showMessageDialog(this, stringBuilder.toString(), "Climbed Ladder of each Piece", JOptionPane.INFORMATION_MESSAGE);
    }

    //endregion

    //region getters

    int getSnakeJListSelectedItem() {
        return snakeJList.getSelectedIndex();
    }

    int getHumanListItem() {
        return humanJList.getSelectedIndex();
    }

//    int getSquareListItem(){
//
//        int value = -1;
//        try {
//            value = Integer.parseInt(validMovesList.getSelectedValue());
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return value;
//    }

    private String getParalyzedText(int i) {
        return (i + 1) + ": " + gameModel.getHumanList().get(i).getParalyzeTurns();
    }

    DiceView getDiceView() {
        return diceView;
    }

    void showValidMoves(int humanPiece) {
        if (humanPiece <= gameModel.getHumanList().size() && humanPiece >= 0) {

            for (JButton humanKnightMoveButton : humanKnightMoveButtons) {
                remove(humanKnightMoveButton);
            }

            humanKnightMoveButtons.clear();

            for (String validKnightMove : gameModel.getHumanList().get(humanPiece).getValidKnightMoves(gameModel.getSquares())) {
                JButton humanKnighButton = new JButton(validKnightMove);
                humanKnighButton.setPreferredSize(new Dimension(100, 30));
                humanKnighButton.addActionListener(this);

                humanKnightMoveButtons.add(humanKnighButton);

                //add(humanKnighButton);
            }

            String[] validMoves = new String[gameModel.getHumanList().get(humanPiece).getValidKnightMoves(gameModel.getSquares()).length];

            for (int i = 0; i < validMoves.length; i++) {
                validMoves[i] = gameModel.getHumanList().get(humanPiece).getValidKnightMoves(gameModel.getSquares())[i];
            }

            validMovesList.setBackground(background);
            validMovesList.setBorder(new LineBorder(Color.BLACK));

            validMovesList.setEnabled(true);
            validMovesList.setVisible(true);

            validMovesList.setListData(validMoves);
            add(validMovesList);
            moveKnightBut.setEnabled(true);
            moveKnightBut.setVisible(true);
            add(moveKnightBut);

            revalidate();
            repaint();

            //String[] test = gameModel.getHumanList().get(humanPiece).getValidKnightMoves(gameModel.getSquares());
            //validMovesList.setListData(test);
            //add(new JButton("Move Piece"));
        } else {
            JOptionPane.showMessageDialog(this, "please select a piece to move");
        }

    }

    int getMovesSquareItem() {
        try {
            return Integer.parseInt(validMovesList.getSelectedValue());
        } catch (NumberFormatException e) {
            return -1;
        }

    }

    void setOnDiceViewEventListener(GameController listener) {
        diceView.setOnViewEventListener(listener);
    }

    //end region
}
