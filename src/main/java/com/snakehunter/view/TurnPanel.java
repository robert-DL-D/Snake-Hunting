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

    private final String turnNoString = "Turn No: ";
    private final String stageLabelString = "Stage: ";
    private final String playerturnString = "Player turn: ";

    private final String[] humanButtons = {"Roll Dice", "Place Guard"};
    private final String[] snakeButtons = {"Move Up", "Move Down", "Move Left", "Move Right"};

    private final List<JButton> hButtons = new ArrayList<JButton>();
    private final List<JButton> sButtons = new ArrayList<JButton>();

    private ActionListener listener;

    private GameModel gameModel;
    private JLabel turnNoLabel;
    private JLabel stageNoLabel;
    private JLabel turntakerLabel;


    public TurnPanel(ActionListener listener, GameModel gameModel) {
        this.listener = listener;
        this.gameModel = gameModel;

        setSize(150, 400);

        stageNoLabel = new JLabel((stageLabelString + gameModel.getGameStage()));
        stageNoLabel.setPreferredSize(new Dimension(150, 50));
        add(stageNoLabel);

        turnNoLabel= new JLabel(turnNoString + gameModel.getNumOfTurns());
        turnNoLabel.setPreferredSize(new Dimension(150, 50));
        add(turnNoLabel);

        turntakerLabel = new JLabel(playerturnString + gameModel.getCurrentPlayer());
        turntakerLabel.setPreferredSize(new Dimension(150, 50));
        add(turntakerLabel);

    }

    public void updateTurnNo(int turnNo){
        turnNoLabel.setText(turnNoString + turnNo);
        String s = "Human";
        if (turnNo % 2 == 0){
            s = "Snake";
            showSnakeButtons();
        } else {
            showHumanButtons();
        }
        turntakerLabel.setText(playerturnString + s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(e);
    }

    public void updateStage(GameStage s) {
        stageNoLabel.setText(stageLabelString + s);
    }

    public void showHumanButtons(){
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

    public void showSnakeButtons(){
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
