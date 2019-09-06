package java.com.snakehunter;

import org.junit.BeforeClass;
import org.junit.Test;

import login.LoginView;

import static org.junit.Assert.assertEquals;

public class LoginViewTest {
    private static LoginView loginView;

    @BeforeClass
    public static void setUp() {
        loginView = new LoginView();

    }

    @Test
    public void testLogin() {

        assertEquals(loginView.testValidateLoginDetails("", ""), false);
        assertEquals(loginView.testValidateLoginDetails("Human", "password"), true);
        assertEquals(loginView.testValidateLoginDetails("human", "Password"), false);
        assertEquals(loginView.testValidateLoginDetails("Snake", "Hunter2"), true);

    }
}