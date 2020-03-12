package com.agisoft.account.authorization;

import com.agisoft.account.AccountFunctionalTest;
import com.agisoft.account.utils.pages.AccountLoginPage;
import com.agisoft.account.utils.pages.GeneralPage;
import com.agisoft.utils.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.agisoft.account.utils.AccountTestProvision.cancelPasswordResetIfNecessary;
import static com.agisoft.account.utils.AccountTestProvision.changePassword;
import static com.codeborne.selenide.Selenide.open;

public class SuccessfulAuthorizationWithChangedPasswordUITest extends AccountFunctionalTest {

    private User testUser;
    private GeneralPage generalPage;
    private static final String CHANGED_PASSWORD = "newPassword";

    @Before
    public void setUp() {
        testUser = users.getAnotherUser();
        changePassword(testUser, CHANGED_PASSWORD);
    }

    @Test
    public void userAuthorization_shouldBe_successful_when_restoredPassword() {
        generalPage = open(AccountLoginPage.RELATIVE_URL, AccountLoginPage.class)
                .verifyAbsenceOfErrors()
                .enterEmail(testUser.getEmail())
                .verifyAbsenceOfErrors()
                .enterPassword(CHANGED_PASSWORD)
                .verifyAbsenceOfErrors()
                .logIn(testUser);
    }

    @After
    public void tearDown() {
        cancelPasswordResetIfNecessary(CHANGED_PASSWORD, testUser, generalPage);
    }
}
