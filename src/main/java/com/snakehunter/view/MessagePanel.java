package com.snakehunter.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class MessagePanel extends JPanel {

    private final JTextArea jTextArea;

    MessagePanel() {

        setSize(320, 520);

        jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setSize(300, 300);
        jTextArea.setText("Turn Messages:");

        JScrollPane jScrollPane = new JScrollPane(jTextArea);

        jScrollPane.setSize(300, 480);

        add(jScrollPane);
    }

    void displayMessage(String message) {
        jTextArea.setText(jTextArea.getText() + "\n" + message);
    }
}
