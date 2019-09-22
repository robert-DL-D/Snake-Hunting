package com.snakehunter.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class LoginView {
    private static JFrame JFRAME = new JFrame("Login");
    private static final String DELIMITER = "_";

    private JPanel jPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameTxtF;
    private JPasswordField passwordTxtF;
    private JButton loginButton;
    private JTextField newUsernameTxtF;
    private JPasswordField newPasswordTxtF;
    private JButton newAccountButton;
    private JLabel humanPlayerLabel;
    private JLabel snakePlayerLabel;
    private JLabel loginMessages;
    private JLabel newAccountMessages;

    private final File file = new File("src/main/java/com/snakehunter/file/UsernamePassword.txt");
    private HashMap<String, String> usernamePassword = new HashMap<>();

    //TODO Add login for both human and snake player
    private String[] playersUsername = new String[2];

    private boolean loginSuccess = false;

    //TODO Connect Login with the main game class
    public static void main(String[] args) {
        JFRAME.setContentPane(new LoginView().jPanel);
        JFRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFRAME.pack();
        JFRAME.setLocationRelativeTo(null);
        JFRAME.setResizable(false);
        JFRAME.setVisible(true);

        System.out.println(instruction());
    }

    private static String instruction() {
        return "Accounts for testing:\n"
                + "HUMAN | password\n"
                + "SNAKE | Hunter2\n"
                + "APPLE | banana\n"
                + "USERNAME | password\n";
    }

    public LoginView() {

        importUsernamePassword();

        loginButton.addActionListener(e -> validateLogin());

        passwordTxtF.addActionListener(e -> validateLogin());

        newPasswordTxtF.addActionListener(e -> createNewAccount());

        newAccountButton.addActionListener(e -> createNewAccount());

        // This is to allow tabbing to go from Username to Password
        JFRAME.setFocusTraversalPolicy(new MyOwnFocusTraversalPolicy(getTextFieldsVector()));

        loginMessages.setForeground(Color.RED);
        newAccountMessages.setForeground(Color.RED);
    }

    private Vector<Component> getTextFieldsVector() {
        Vector<Component> textFields = new Vector<>(4);
        textFields.add(usernameTxtF);
        textFields.add(passwordTxtF);
        textFields.add(newUsernameTxtF);
        textFields.add(newPasswordTxtF);
        return textFields;
    }

    private void importUsernamePassword() {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st = null;
            while (true) {
                try {
                    if ((st = br.readLine()) == null) {
                        break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                String[] newUsernamePassword = st.split(DELIMITER);
                usernamePassword.put(newUsernamePassword[0], newUsernamePassword[1]);

            }
        } catch (FileNotFoundException e) {

            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void createNewAccount() {

        if (newUsernameTxtF.getText().length() != 0 && newPasswordTxtF.getPassword().length != 0) {

            String newUsername = newUsernameTxtF.getText().toUpperCase();

            // Write to file immediately if there are no record
            if (usernamePassword.size() == 0) {
                writeToFile();
                setPlayerUsername(newUsername);
                loginSuccess = true;
            }

            for (Iterator<Entry<String, String>> iterator = usernamePassword.entrySet().iterator(); iterator
                    .hasNext(); ) {

                Entry<String, String> entry = iterator.next();
                if (entry.getKey().equals(newUsername)) {
                    newAccountMessages.setText("Username already exists");
                    loginSuccess = false;
                    break;
                } else if (!iterator.hasNext()) {
                    writeToFile();
                    setPlayerUsername(newUsername);
                    loginSuccess = true;

                }
            }
        } else {
            newAccountMessages.setText("Please fill in username or password");
            loginSuccess = false;
        }
    }

    private void writeToFile() {

        String newUsername = newUsernameTxtF.getText().toUpperCase();
        String newHashedPassword = hashPassword(newPasswordTxtF.getPassword());

        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(System.lineSeparator());
            fileWriter.write(newUsername + DELIMITER + newHashedPassword);
            fileWriter.close();

            usernamePassword.put(newUsername, newHashedPassword);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("New account added");
        //JFRAME.setVisible(false);
    }

    private void setPlayerUsername(String username) {
        if (playersUsername[0] == null) {
            playersUsername[0] = username;
            humanPlayerLabel.setText("Human Player: " + username);
        } else {
            playersUsername[1] = username;
            snakePlayerLabel.setText("Snake Player: " + username);
        }

        usernameTxtF.setText("");
        passwordTxtF.setText("");
        newUsernameTxtF.setText("");
        newPasswordTxtF.setText("");
    }

    public void validateLogin() {
        String username = usernameTxtF.getText().toUpperCase();
        String hashedPassword = hashPassword(passwordTxtF.getPassword());

        for (Iterator<Entry<String, String>> iterator = usernamePassword.entrySet().iterator(); iterator.hasNext(); ) {
            Entry<String, String> entry = iterator.next();
            if (entry.getKey().equals(username) && entry.getValue().equals(hashedPassword)) {
                setPlayerUsername(username);
                //JFRAME.setVisible(false);
                loginSuccess = true;
                break;
            } else if (!iterator.hasNext()) {
                loginMessages.setText("Invalid username or password");
                loginSuccess = false;
            }
        }

    }

    private String hashPassword(char[] password) {
        StringBuilder sb = new StringBuilder();
        String passwordToBeHash = Arrays.toString(password);

        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            //Add password bytes to digest
            md.update(passwordToBeHash.getBytes());

            //Get the hash's bytes
            byte[] bytes = md.digest();

            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Get complete hashed password in hex format
        return sb.toString();
    }

    public void setUsernameTxtF(String username) {
        usernameTxtF.setText(username);
    }

    public void setPasswordTxtF(String password) {
        passwordTxtF.setText(password);
    }

    public void setNewUsernameTxtF(String newUsername) {
        newUsernameTxtF.setText(newUsername);
    }

    public void setNewPasswordTxtF(String newPassword) {
        newPasswordTxtF.setText(newPassword);
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
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
        jPanel.setLayout(new GridLayoutManager(6, 4, new Insets(20, 10, 20, 10), -1, -1));
        jPanel.setMaximumSize(new Dimension(-1, -1));
        jPanel.setMinimumSize(new Dimension(-1, -1));
        jPanel.setPreferredSize(new Dimension(520, 230));
        jPanel.setBorder(BorderFactory.createTitledBorder(null, "Snake Hunting", TitledBorder.CENTER, TitledBorder.BELOW_TOP, this.$$$getFont$$$(null, Font.BOLD | Font.ITALIC, 20, jPanel.getFont()), new Color(-16777216)));
        newAccountButton = new JButton();
        Font newAccountButtonFont = this.$$$getFont$$$(null, -1, 14, newAccountButton.getFont());
        if (newAccountButtonFont != null) newAccountButton.setFont(newAccountButtonFont);
        newAccountButton.setText("New Account");
        jPanel.add(newAccountButton, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(140, -1), new Dimension(140, -1), 0, false));
        newPasswordTxtF = new JPasswordField();
        newPasswordTxtF.setColumns(1);
        newPasswordTxtF.setFocusTraversalPolicyProvider(false);
        Font newPasswordTxtFFont = this.$$$getFont$$$(null, -1, 14, newPasswordTxtF.getFont());
        if (newPasswordTxtFFont != null) newPasswordTxtF.setFont(newPasswordTxtFFont);
        jPanel.add(newPasswordTxtF, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(130, -1), new Dimension(130, -1), 0, false));
        newUsernameTxtF = new JTextField();
        newUsernameTxtF.setColumns(1);
        newUsernameTxtF.setFocusTraversalPolicyProvider(false);
        Font newUsernameTxtFFont = this.$$$getFont$$$(null, -1, 14, newUsernameTxtF.getFont());
        if (newUsernameTxtFFont != null) newUsernameTxtF.setFont(newUsernameTxtFFont);
        jPanel.add(newUsernameTxtF, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(130, -1), new Dimension(130, -1), 0, false));
        usernameLabel = new JLabel();
        Font usernameLabelFont = this.$$$getFont$$$(null, Font.BOLD, 16, usernameLabel.getFont());
        if (usernameLabelFont != null) usernameLabel.setFont(usernameLabelFont);
        usernameLabel.setText("Username");
        jPanel.add(usernameLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordLabel = new JLabel();
        Font passwordLabelFont = this.$$$getFont$$$(null, Font.BOLD, 16, passwordLabel.getFont());
        if (passwordLabelFont != null) passwordLabel.setFont(passwordLabelFont);
        passwordLabel.setText("Password");
        jPanel.add(passwordLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordTxtF = new JPasswordField();
        passwordTxtF.setColumns(1);
        passwordTxtF.setFocusTraversalPolicyProvider(false);
        Font passwordTxtFFont = this.$$$getFont$$$(null, -1, 14, passwordTxtF.getFont());
        if (passwordTxtFFont != null) passwordTxtF.setFont(passwordTxtFFont);
        jPanel.add(passwordTxtF, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 26), new Dimension(130, -1), 0, false));
        usernameTxtF = new JTextField();
        usernameTxtF.setColumns(1);
        usernameTxtF.setFocusCycleRoot(false);
        usernameTxtF.setFocusTraversalPolicyProvider(false);
        Font usernameTxtFFont = this.$$$getFont$$$(null, -1, 14, usernameTxtF.getFont());
        if (usernameTxtFFont != null) usernameTxtF.setFont(usernameTxtFFont);
        jPanel.add(usernameTxtF, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 26), new Dimension(130, -1), 0, false));
        loginButton = new JButton();
        Font loginButtonFont = this.$$$getFont$$$(null, -1, 14, loginButton.getFont());
        if (loginButtonFont != null) loginButton.setFont(loginButtonFont);
        loginButton.setText("Login");
        jPanel.add(loginButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 24), new Dimension(100, -1), 0, false));
        snakePlayerLabel = new JLabel();
        Font snakePlayerLabelFont = this.$$$getFont$$$(null, -1, 18, snakePlayerLabel.getFont());
        if (snakePlayerLabelFont != null) snakePlayerLabel.setFont(snakePlayerLabelFont);
        snakePlayerLabel.setText("Snake Player: Waiting");
        jPanel.add(snakePlayerLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 25), null, 0, false));
        humanPlayerLabel = new JLabel();
        Font humanPlayerLabelFont = this.$$$getFont$$$(null, -1, 18, humanPlayerLabel.getFont());
        if (humanPlayerLabelFont != null) humanPlayerLabel.setFont(humanPlayerLabelFont);
        humanPlayerLabel.setText("Human Player: Waiting");
        jPanel.add(humanPlayerLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 25), null, 0, false));
        newAccountMessages = new JLabel();
        newAccountMessages.setForeground(new Color(-16777216));
        newAccountMessages.setText("");
        newAccountMessages.setVisible(true);
        jPanel.add(newAccountMessages, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loginMessages = new JLabel();
        loginMessages.setEnabled(true);
        loginMessages.setFocusable(false);
        loginMessages.setText("");
        loginMessages.setVisible(true);
        jPanel.add(loginMessages, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(196, 10), null, 0, false));
        final Spacer spacer1 = new Spacer();
        jPanel.add(spacer1, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(68, 14), null, 0, false));
        final JSeparator separator1 = new JSeparator();
        separator1.setOrientation(1);
        jPanel.add(separator1, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        separator2.setOrientation(1);
        jPanel.add(separator2, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

    public String getHumanUsername() {
        return playersUsername[0];
    }

    public String getSnakeUsername() {
        return playersUsername[1];
    }

    static class MyOwnFocusTraversalPolicy
            extends FocusTraversalPolicy {

        private Vector<Component> textFields;

        MyOwnFocusTraversalPolicy(Vector<Component> textFields) {

            this.textFields = new Vector<>(textFields.size());
            this.textFields.addAll(textFields);
        }

        public Component getComponentAfter(Container focusCycleRoot,
                                           Component aComponent) {
            int idx = (textFields.indexOf(aComponent) + 1) % textFields.size();
            return textFields.get(idx);
        }

        public Component getComponentBefore(Container focusCycleRoot,
                                            Component aComponent) {
            int idx = textFields.indexOf(aComponent) - 1;
            if (idx < 0) {
                idx = textFields.size() - 1;
            }
            return textFields.get(idx);
        }

        public Component getDefaultComponent(Container focusCycleRoot) {
            return textFields.get(0);
        }

        public Component getLastComponent(Container focusCycleRoot) {
            return textFields.get(textFields.size() - 1);
        }

        public Component getFirstComponent(Container focusCycleRoot) {
            return textFields.get(0);
        }

    }

}





