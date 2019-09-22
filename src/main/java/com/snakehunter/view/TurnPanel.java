package com.snakehunter.view;

import com.snakehunter.GameContract.GameModel;
import com.snakehunter.GameStage;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class TurnPanel
        extends JPanel
        implements ActionListener {
    private final String saveGameString = "Save Game";

    private final String turnNoString = "Turn No: %s /50";
    private final String stageLabelString = "Stage: ";
    private final String playerturnString = "Player turn: ";
    private final String humanGuardString = "Guards left: ";

    private final String[] humanButtons = {"Roll Dice", "Place Guard"};
    private final String[] snakeButtons = {"Move Up", "Move Down", "Move Left", "Move Right"};

    private final List<JButton> hButtons = new ArrayList<>();
    private final List<JButton> sButtons = new ArrayList<>();

    private ActionListener listener;

    private GameModel gameModel;
    private JLabel turnNoLabel;
    private JLabel stageNoLabel;
    private JLabel turntakerLabel;
    private JLabel guardLabel;
    private StringBuilder sb = new StringBuilder();

    private JButton saveGameButton;
    public TurnPanel(ActionListener listener, GameModel gameModel) {
        this.listener = listener;
        this.gameModel = gameModel;

        setSize(150, 400);

        saveGameButton = new JButton(saveGameString);
        saveGameButton.setPreferredSize(new Dimension(150, 50));
        saveGameButton.addActionListener(this);
        add(saveGameButton);

        stageNoLabel = new JLabel((stageLabelString + gameModel.getGameStage()));
        stageNoLabel.setPreferredSize(new Dimension(150, 25));
        add(stageNoLabel);

        sb.append(String.format("Turn No: %s 50", gameModel.getNumOfTurns()));
        //gameModel.getNumOfTurns()
        turnNoLabel= new JLabel();
        turnNoLabel.setPreferredSize(new Dimension(150, 25));
        turnNoLabel.setText(sb.toString());
        add(turnNoLabel);

        guardLabel = new JLabel();
        guardLabel.setPreferredSize(new Dimension(150, 25));
        guardLabel.setText(humanGuardString + gameModel.getNumOfGuards());
        add(guardLabel);

        turntakerLabel = new JLabel(playerturnString + gameModel.getCurrentPlayer());
        turntakerLabel.setPreferredSize(new Dimension(150, 25));
        add(turntakerLabel);

    }

    public void updateTurnNo(int turnNo){
        sb = new StringBuilder();
        sb.append(String.format(turnNoString, gameModel.getNumOfTurns()));
        turnNoLabel.setText(sb.toString());
        String s = "Human";
        if (turnNo % 2 == 0){
            s = "Snake";
            showSnakeButtons();
        } else {
            showHumanButtons();
        }
        turntakerLabel.setText(playerturnString + s);
    }

    public void updateGuardNo(){
        guardLabel.setText(humanGuardString + gameModel.getNumOfGuards());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(e);
    }

    public void updateStage(GameStage s) {
        stageNoLabel.setText(stageLabelString + s);
    }

    private void showHumanButtons(){
        for(JButton b : sButtons){
            remove(b);
        }
        repaint();
        sButtons.clear();
        for (String buttonStr : humanButtons) {
            JButton button = new JButton(buttonStr);
            hButtons.add(button);
            button.setPreferredSize(new Dimension(150, 50));
            button.addActionListener(this);
            add(button);
        }
    }

    private void showSnakeButtons(){
        for(JButton b : hButtons){
            remove(b);
        }
        repaint();
        hButtons.clear();
        for (String buttonStr : snakeButtons) {
            JButton button = new JButton(buttonStr);
            sButtons.add(button);
            button.setPreferredSize(new Dimension(150, 50));
            button.addActionListener(this);
            add(button);
        }
    }
}
