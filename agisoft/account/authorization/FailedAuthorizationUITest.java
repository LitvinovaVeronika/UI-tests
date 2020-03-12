package com.agisoft.account.authorization;

import com.agisoft.account.AccountFunctionalTest;
import com.agisoft.account.utils.pages.AccountLoginPage;
import com.agisoft.utils.email.EmailCreator;
import com.agisoft.utils.users.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

public class FailedAuthorizationUITest extends AccountFunctionalTest {

    private static User testUser;

    @BeforeClass
    public static void setUp() {
       testUser = users.getNormalUser();
    }

    @Test
    public void userAuthorization_shouldBe_failed_when_emptyUserInfo() {
        open(AccountLoginPage.RELATIVE_URL, AccountLoginPage.class)
                .verifyAbsenceOfErrors()
                .logIn()
                .verifyPage();
    }

    @Test
    public void userAuthorization_shouldBe_failed_when_fakeUserInfo() {
        String fakeEmail = EmailCreator.getRandomEmail();
        String fakePassword = RandomStringUtils.randomAlphanumeric(6);

        open(AccountLoginPage.RELATIVE_URL, AccountLoginPage.class)
                .verifyAbsenceOfErrors()
                .enterEmail(fakeEmail)
                .verifyAbsenceOfErrors()
                .enterPassword(fakePassword)
                .verifyAbsenceOfErrors()
                .logIn()
                .verifyErrors();
    }

    @Test
    public void userAuthorization_shouldBe_failed_when_wrongUserPassword() {
        String wrongPassword = RandomStringUtils.randomAlphanumeric(6);

        open(AccountLoginPage.RELATIVE_URL, AccountLoginPage.class)
                .verifyAbsenceOfErrors()
                .enterEmail(testUser.getEmail())
                .verifyAbsenceOfErrors()
                .enterPassword(wrongPassword)
                .verifyAbsenceOfErrors()
                .logIn()
                .verifyErrors();
    }

    @Test
    public void userAuthorization_shouldBe_failed_when_newFailedTry() {
        String fakeEmail = EmailCreator.getRandomEmail();
        String fakePassword = RandomStringUtils.randomAlphanumeric(6);

        open(AccountLoginPage.RELATIVE_URL, AccountLoginPage.class)
                .verifyAbsenceOfErrors()
                .enterEmail(testUser.getEmail())
                .verifyAbsenceOfErrors()
                .enterPassword(fakePassword)
                .verifyAbsenceOfErrors()
                .logIn()
                .verifyErrors()
                .enterEmail(fakeEmail)
                .verifyAbsenceOfErrors()
                .enterPassword(fakePassword)
                .verifyAbsenceOfErrors()
                .logIn()
                .verifyErrors();
    }

    @After
    public void tearDown() {
        clearBrowserCookies();
    }
}
