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
    private static final String saveGameString = "Save Game";
    private final String turnNoString = "Turn No: %s/%s";
    private final String stageLabelString = "Stage: ";
    private final String playerturnString = " turn: ";
    private final String humanGuardString = "Guards left: ";
    private static final String PIECES_PARALYSED_TURN = "Pieces Paralysed Turn";
    private static final String PIECE = "Piece";

    private boolean showPieceButtons = false;

    private final String[] humanButtons = {"Roll Dice", "Place Guard"};
    private final String[] humanFinalButtons = {"Show Valid Moves"};
    private final String[] pieceButtons = {"1", "2", "3", "4"};
    //private final String[] snakeButtons = {"Move Up", "Move Down", "Move Left", "Move Right"};
    private final String[] wideSnakeButtons = {"Up", "Down"};
    private final String[] longSnakeButtons = {"Left", "Right"};

    private final List<JButton> hButtons = new ArrayList<>();
    private final List<JButton> pButtons = new ArrayList<>();
    private LinkedList<JLabel> paralyzedLabels = new LinkedList<>();
    private final List<JButton> sButtons = new ArrayList<>();

    private ActionListener listener;

    private GameModel gameModel;
    private JLabel turnNoLabel;
    private JLabel stageNoLabel;
    private JLabel turntakerLabel;
    private JLabel guardLabel;
    private JLabel knightLabel = new JLabel("Choose a human to move:");
    private JList<String> snakeJList;
    private JList<String> humanJList;
    private JList<String> validMovesList;
    private StringBuilder sb = new StringBuilder();

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
            paralyzedLabels.add(jLabel);
            add(jLabel);
        }

        snakeJList = new JList<>();
        snakeJList.setBackground(background);
        snakeJList.setBorder(new LineBorder(Color.BLACK));

        humanJList = new JList<>();
        humanJList.setBackground(background);
        humanJList.setBorder(new LineBorder(Color.BLACK));

        validMovesList = new JList<>();
        validMovesList.setBackground(background);
        validMovesList.setBorder(new LineBorder(Color.BLACK));

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

        updateParalyzedTurn();
    }

    public void updateGuardNo() {
        guardLabel.setText(humanGuardString + gameModel.getRemainingGuards());
    }

    void updateParalyzedTurn() {
        for (int i = 0; i < paralyzedLabels.size(); i++) {
            paralyzedLabels.get(i).setText(PIECE + " " + (i + 1) + ": " + gameModel.getHumanList().get(i).getParalyzeTurns());
        }
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

        if (gameModel.getGameStage() == GameStage.SECOND) {
            for (String buttonStr : humanButtons) {
                JButton button = new JButton(buttonStr);
                hButtons.add(button);
                button.setPreferredSize(new Dimension(150, 35));
                button.addActionListener(this);
                add(button);
            }
        } else {
            showHumanKnightButtons();
            for (String buttonStr : humanFinalButtons) {
                JButton button = new JButton(buttonStr);
                hButtons.add(button);
                button.setPreferredSize(new Dimension(150, 35));
                button.addActionListener(this);
                add(button);
            }
            add(validMovesList);

        }

    }

    public void addPieceButtons() {
        for (String pieceString : pieceButtons) {
            JButton button = new JButton(pieceString);
            pButtons.add(button);
            button.setPreferredSize(new Dimension(50, 30));
            button.addActionListener(this);
            add(button);
        }

        showPieceButtons = true;
    }

    private void removePieceButtons() {
        for (JButton b : pButtons) {
            remove(b);
        }
        repaint();
        pButtons.clear();
        showPieceButtons = false;
    }

    public void showPieceButtons() {
        for (JButton b : pButtons) {
            b.setVisible(true);
        }
        showPieceButtons = true;
    }

    public void hidePieceButtons() {
        for (JButton b : pButtons) {
            b.setVisible(false);
        }
        showPieceButtons = false;
    }

    public boolean getShowPieceButtons() {
        return showPieceButtons;
    }

    private void showSnakeButtons() {
        for (JButton b : hButtons) {
            remove(b);
        }
        for (JButton b : sButtons) {
            remove(b);
        }

        removePieceButtons();
        repaint();
        hButtons.clear();

        List<Snake> snakeList = gameModel.getSnakeList();
        String[] snakePosArray = new String[snakeList.size()];
        for (int i = 0; i < snakeList.size(); i++) {
            Snake snake = snakeList.get(i);
            snakePosArray[i] = ("Snake " + (i + 1) + ": " + snake.getPosition() + " " + snake.getConnectedPosition());
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

    private void showHumanKnightButtons() {
        add(knightLabel);
        String[] humanPieces = new String[gameModel.getHumanList().size()];
        for (int i = 0; i < humanPieces.length; i++) {
            humanPieces[i] = ("Human " + (i + 1) + " @ pos " + gameModel.getHumanList().get(i).getPosition());
        }
        humanJList.setListData(humanPieces);
        add(humanJList);
    }

    //endregion

    //region getters

    int getJListSelectItem() {
        return snakeJList.getSelectedIndex();
    }

    int getHumanListItem() {
        return humanJList.getSelectedIndex();
    }

    public List<JButton> getpButtons() {
        return pButtons;
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

    public void showValidMoves(int humanPiece) {
        if (humanPiece <= gameModel.getHumanList().size() && humanPiece >= 0) {
            String[] test = gameModel.getHumanList().get(humanPiece).getValidKnightMoves(gameModel.getSquares());
            validMovesList.setListData(test);
            add(new JButton("Move Piece"));
        } else {
            JOptionPane.showMessageDialog(this, "please select a piece to move");
        }

    }
    //end region
}
