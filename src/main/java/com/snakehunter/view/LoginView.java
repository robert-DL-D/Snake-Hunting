package com.snakehunter.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class LoginView {
    private static final JFrame JFRAME = new JFrame("Login");
    private JTextField usernameTxtF;
    private JPasswordField passwordTxtF;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel jPanel;
    private JButton loginButton;

    private String username;

    private static final String TEST_USERNAME_1 = "Human";
    private static final char[] TEST_PASSWORD_1 = new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};

    private static final String TEST_USERNAME_2 = "Snake";
    private static final char[] TEST_PASSWORD_2 = new char[]{'H', 'u', 'n', 't', 'e', 'r', '2'};

    private HashMap<String, char[]> usernamePassword = new HashMap<>();

    public LoginView() {

        usernamePassword.put(TEST_USERNAME_1, TEST_PASSWORD_1);
        usernamePassword.put(TEST_USERNAME_2, TEST_PASSWORD_2);

        loginButton.addActionListener(e ->

                validateLoginDetails());

        passwordTxtF.addActionListener(e ->

                validateLoginDetails());
    }

    private void validateLoginDetails() {
        username = usernameTxtF.getText();
        char[] password = passwordTxtF.getPassword();

        for (Iterator<Map.Entry<String, char[]>> iterator = usernamePassword.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, char[]> entry = iterator.next();
            if (entry.getKey().equals(username) && Arrays.equals(entry.getValue(), password)) {
                System.out.println("Login successfully");
                JFRAME.setVisible(false);
                break;
            } else if (!iterator.hasNext()) {
                System.out.println("Invalid username or password");
            }
        }

    }

    public static void main(String[] args) {
        JFRAME.setContentPane(new LoginView().jPanel);
        JFRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFRAME.pack();
        JFRAME.setLocationRelativeTo(null);
        JFRAME.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        jPanel = new JPanel();
        jPanel.setLayout(new GridLayoutManager(3, 2, new Insets(20, 10, 20, 10), -1, -1));
        jPanel.setPreferredSize(new Dimension(300, 180));
        jPanel.setBorder(BorderFactory.createTitledBorder(null, "Snake Hunting", TitledBorder.CENTER, TitledBorder.BELOW_TOP, this.$$$getFont$$$(null, Font.BOLD | Font.ITALIC, 20, jPanel.getFont()), new Color(-16777216)));
        usernameLabel = new JLabel();
        Font usernameLabelFont = this.$$$getFont$$$(null, Font.BOLD, 16, usernameLabel.getFont());
        if (usernameLabelFont != null) usernameLabel.setFont(usernameLabelFont);
        usernameLabel.setText("Username");
        jPanel.add(usernameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        usernameTxtF = new JTextField();
        Font usernameTxtFFont = this.$$$getFont$$$(null, -1, 14, usernameTxtF.getFont());
        if (usernameTxtFFont != null) usernameTxtF.setFont(usernameTxtFFont);
        jPanel.add(usernameTxtF, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordLabel = new JLabel();
        Font passwordLabelFont = this.$$$getFont$$$(null, Font.BOLD, 16, passwordLabel.getFont());
        if (passwordLabelFont != null) passwordLabel.setFont(passwordLabelFont);
        passwordLabel.setText("Password");
        jPanel.add(passwordLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordTxtF = new JPasswordField();
        Font passwordTxtFFont = this.$$$getFont$$$(null, -1, 14, passwordTxtF.getFont());
        if (passwordTxtFFont != null) passwordTxtF.setFont(passwordTxtFFont);
        jPanel.add(passwordTxtF, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        loginButton = new JButton();
        Font loginButtonFont = this.$$$getFont$$$(null, -1, 14, loginButton.getFont());
        if (loginButtonFont != null) loginButton.setFont(loginButtonFont);
        loginButton.setText("Login");
        jPanel.add(loginButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jPanel;
    }

    public String getUsername() {
        return username;
    }

}
