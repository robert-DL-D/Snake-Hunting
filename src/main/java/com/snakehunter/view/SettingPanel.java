package com.snakehunter.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author WeiYi Yu
 * @date 2019-09-02
 */
public class SettingPanel
        extends JPanel
        implements ActionListener {

    private final String[] buttons = {"Add Snake", "Add Ladder", "Add Humans", "Start"};

    private ActionListener listener;

    public SettingPanel(ActionListener listener) {
        this.listener = listener;

        setSize(150, 400);

        for (String buttonStr : buttons) {
            JButton button = new JButton(buttonStr);
            button.setPreferredSize(new Dimension(150, 50));
            button.addActionListener(this);
            add(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.actionPerformed(e);
    }
}
