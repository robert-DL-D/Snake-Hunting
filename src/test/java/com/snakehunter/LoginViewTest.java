package com.snakehunter;

import com.snakehunter.model.exceptions.InvalidDetailException;
import com.snakehunter.view.LoginView;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginViewTest {
    private static final String EMPTY_USERNAME = "";
    private static final char[] EMPTY_PASSWORD = new char[0];
    private LoginView loginView;

    @Before
    public void setUp() {
        loginView = new LoginView();
    }

    // Start of Login Tests
    private void assertEmptyLogin() {
        assertEquals(EMPTY_USERNAME, loginView.getUsernameTxtF().getText());
        assertArrayEquals(EMPTY_PASSWORD, loginView.getPasswordTxtF().getPassword());
    }

    // Positive Test 1
    @Test
    public void successLoginTest() {

        assertEmptyLogin();

        loginView.setUsernameTxtF("human");
        loginView.setPasswordTxtF("password");

        try {
            loginView.validateLogin();
        } catch (InvalidDetailException e) {
            e.printStackTrace();
        }

        assertTrue(loginView.isLoginSuccess());
    }

    // Positive Test 2
    @Test
    public void successLoginTest2() {

        assertEmptyLogin();

        loginView.setUsernameTxtF("HuMaN");
        loginView.setPasswordTxtF("password");

        try {
            loginView.validateLogin();
        } catch (InvalidDetailException e) {
            e.printStackTrace();
        }

        assertTrue(loginView.isLoginSuccess());
    }

    // Negative Test 1
    @Test
    public void wrongCaseLoginTest() {

        assertEmptyLogin();

        loginView.setUsernameTxtF("human");
        loginView.setPasswordTxtF("Password");
        try {
            loginView.validateLogin();
        } catch (InvalidDetailException e) {
            e.printStackTrace();
        }

        assertFalse(loginView.isLoginSuccess());
    }

    // Negative Test 2
    @Test
    public void emptyLoginTest() {

        assertEmptyLogin();

        try {
            loginView.validateLogin();
        } catch (InvalidDetailException e) {
            e.printStackTrace();
        }

        assertFalse(loginView.isLoginSuccess());
    }
    // End of Login Tests

    // Start of New Account Tests
    private void assertEmptyNewAccount() {
        assertEquals(EMPTY_USERNAME, loginView.getNewUsernameTxtF().getText());
        assertArrayEquals(EMPTY_PASSWORD, loginView.getNewPasswordTxtF().getPassword());
    }

    // Positive Test 1 & 2
    @Test
    public void createNewAccountAndLoginTest() {

        assertEmptyNewAccount();

        String newAccountName = getRandomString();
        loginView.setNewUsernameTxtF(newAccountName);

        String newAccountPW = getRandomString();
        loginView.setNewPasswordTxtF(newAccountPW);

        try {
            loginView.createNewAccount();
        } catch (InvalidDetailException e) {
            e.printStackTrace();
        }

        assertTrue(loginView.isLoginSuccess());

        loginView.setUsernameTxtF(newAccountName);
        loginView.setPasswordTxtF(newAccountPW);
        try {
            loginView.validateLogin();
        } catch (InvalidDetailException e) {
            e.printStackTrace();
        }

        assertTrue(loginView.isLoginSuccess());
    }

    // For generating a random string length of 6 for both username and password
    private String getRandomString() {

        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 6; i++) {

            int j = random.nextInt(36);

            char c = 'a';
            int k;

            if (j < 26) {
                c = (char) (j + c);
                stringBuilder.append(c);
            } else {
                k = (j - 26);
                stringBuilder.append(k);
            }

        }
        return stringBuilder.toString();
    }

    // Negative Test 1
    @Test
    public void emptyNewAccountTest() {

        assertEmptyNewAccount();

        try {
            try {
                loginView.createNewAccount();
            } catch (InvalidDetailException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        assertFalse(loginView.isLoginSuccess());
    }

    // Negative Test 2
    @Test
    public void createExistingAccountTest() {

        assertEmptyNewAccount();

        loginView.setNewUsernameTxtF("human");
        loginView.setNewPasswordTxtF("password");

        try {
            loginView.createNewAccount();
        } catch (InvalidDetailException ex) {
            ex.printStackTrace();
        }

        assertFalse(loginView.isLoginSuccess());
    }
    // End of New Account Tests
}