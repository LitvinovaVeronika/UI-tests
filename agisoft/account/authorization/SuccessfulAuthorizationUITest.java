package com.agisoft.account.authorization;

import com.agisoft.account.AccountFunctionalTest;
import com.agisoft.account.utils.pages.AccountLoginPage;
import com.agisoft.utils.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

public class SuccessfulAuthorizationUITest extends AccountFunctionalTest {

    private User testUser;

    @Before
    public void setUp() {
        testUser = users.getNormalUser();
    }

    @Test
    public void userAuthorization_shouldBe_successful_when_userData() {

        open(AccountLoginPage.RELATIVE_URL, AccountLoginPage.class)
                .verifyAbsenceOfErrors()
                .enterEmail(testUser.getEmail())
                .verifyAbsenceOfErrors()
                .enterPassword(testUser.getPassword())
                .verifyAbsenceOfErrors()
                .logIn(testUser);
    }

    @Test
    public void userAuthorization_shouldBe_successful_when_successTry_after_failedTry() {

        open(AccountLoginPage.RELATIVE_URL, AccountLoginPage.class)
                .verifyAbsenceOfErrors()
                .enterEmail(testUser.getEmail())
                .verifyAbsenceOfErrors()
                .enterPassword(testUser.getPassword() + "mistake")
                .verifyAbsenceOfErrors()
                .logIn()
                .verifyErrors()
                .enterEmail(testUser.getEmail())
                .verifyAbsenceOfErrors()
                .enterPassword(testUser.getPassword())
                .verifyAbsenceOfErrors()
                .logIn(testUser);
    }

    @After
    public void tearDown() {
        clearBrowserCookies();
    }
}
