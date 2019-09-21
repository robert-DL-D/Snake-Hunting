package com.snakehunter;

import com.snakehunter.view.LoginView;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class LoginViewTest {
    private LoginView loginView;

    @Before
    public void setUp() {
        loginView = new LoginView();

    }

    // Start of Login Tests
    // Positive Test 1
    @Test
    public void successLoginTest() {

        loginView.setUsernameTxtF("Human");
        loginView.setPasswordTxtF("password");
        loginView.validateLogin();

        assertTrue(loginView.isLoginSuccess());

    }

    // Positive Test 2
    @Test
    public void successLoginTest2() {

        loginView.setUsernameTxtF("Snake");
        loginView.setPasswordTxtF("Hunter2");
        loginView.validateLogin();

        assertTrue(loginView.isLoginSuccess());

    }

    // Negative Test 1
    @Test
    public void wrongCaseLoginTest() {

        loginView.setUsernameTxtF("human");
        loginView.setPasswordTxtF("password");
        loginView.validateLogin();

        assertTrue(loginView.isLoginSuccess());

    }

    // Negative Test 2
    @Test
    public void emptyLoginTest() {

        loginView.setUsernameTxtF("");
        loginView.setPasswordTxtF("");
        loginView.validateLogin();

        assertTrue(loginView.isLoginSuccess());

    }
    // End of Login Tests

    // Start of New Account Tests
    // Positive Test 1 & 2
    @Test
    public void createNewAccountAndLoginTest() {

        String newAccountName = getRandomString();
        loginView.setNewUsernameTxtF(newAccountName);

        String newAccountPW = getRandomString();
        loginView.setNewPasswordTxtF(newAccountPW);

        loginView.createNewAccount();

        assertTrue(loginView.isLoginSuccess());

        loginView.setUsernameTxtF(newAccountName);
        loginView.setPasswordTxtF(newAccountPW);
        loginView.validateLogin();

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

        loginView.setNewUsernameTxtF("");
        loginView.setNewPasswordTxtF("");
        loginView.createNewAccount();

        assertTrue(loginView.isLoginSuccess());

    }

    // Negative Test 2
    @Test
    public void createExistingAccountTest() {

        loginView.setNewUsernameTxtF("Human");
        loginView.setNewPasswordTxtF("password");
        loginView.createNewAccount();

        assertTrue(loginView.isLoginSuccess());

    }
    // End of New Account Tests
}