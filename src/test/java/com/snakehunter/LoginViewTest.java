package com.snakehunter;

import com.snakehunter.view.LoginView;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginViewTest {
    private LoginView loginView;

    @Before
    public void setUp() {
        loginView = new LoginView();

    }

    @Test
    public void successLoginTest() {

        loginView.setUsernameTxtF("Human");
        loginView.setPasswordTxtF("password");
        loginView.validateLogin();

        assertTrue(loginView.isLoginSuccess());

    }

    @Test
    public void emptyLoginTest() {

        loginView.setUsernameTxtF("");
        loginView.setPasswordTxtF("");
        loginView.validateLogin();

        assertFalse(loginView.isLoginSuccess());

    }

    @Test
    public void wrongCaseLoginTest() {

        loginView.setUsernameTxtF("human");
        loginView.setPasswordTxtF("password");
        loginView.validateLogin();

        assertFalse(loginView.isLoginSuccess());

    }

    @Test
    public void newLoginTest() {

        loginView.setNewUsernameTxtF(getRandomString());
        loginView.setNewPasswordTxtF(getRandomString());
        loginView.createNewAccount();

        assertTrue(loginView.isLoginSuccess());

    }

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

    @Test
    public void emptyNewLoginTest() {

        loginView.setNewUsernameTxtF("");
        loginView.setNewPasswordTxtF("");
        loginView.createNewAccount();

        assertFalse(loginView.isLoginSuccess());

    }

    @Test
    public void existsLoginTest() {

        loginView.setNewUsernameTxtF("Human");
        loginView.setNewPasswordTxtF("password");
        loginView.createNewAccount();

        assertFalse(loginView.isLoginSuccess());

    }


}