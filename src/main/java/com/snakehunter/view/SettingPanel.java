package com.snakehunter.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class SettingPanel
        extends JPanel
        implements ActionListener {

    private ActionListener listener;

    SettingPanel(ActionListener listener) {
        this.listener = listener;

        setSize(150, 500);
        setBorder(new LineBorder(Color.BLACK));

        String[] buttons = {"Load Game", "Add 5 Random S&L", "Add Snake", "Add 5 Random Snakes", "Add Ladder", "Add 5 Random Ladders", "Start"};
        for (String buttonStr : buttons) {
            JButton button = new JButton(buttonStr);
            button.setPreferredSize(new Dimension(150, 30));
            button.addActionListener(this);
            add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(e);
    }
}
