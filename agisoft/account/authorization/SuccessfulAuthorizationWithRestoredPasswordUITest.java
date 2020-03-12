package com.agisoft.account.authorization;

import com.agisoft.account.AccountFunctionalTest;
import com.agisoft.account.utils.pages.AccountLoginPage;
import com.agisoft.account.utils.pages.GeneralPage;
import com.agisoft.utils.users.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.agisoft.account.utils.AccountTestProvision.cancelPasswordResetIfNecessary;
import static com.agisoft.account.utils.AccountTestProvision.resetPassword;

public class SuccessfulAuthorizationWithRestoredPasswordUITest extends AccountFunctionalTest {

    private User testUser;
    private AccountLoginPage loginPage;
    private GeneralPage generalPage;
    private static final String RESTORED_PASSWORD = "newPassword";

    @Before
    public void setUp() throws Exception {
        testUser = users.getEmptyUser();
        loginPage = resetPassword(testUser, RESTORED_PASSWORD);
    }

    @Test
    public void userAuthorization_shouldBe_successful_when_restoredPassword() {
        generalPage = loginPage
                .verifyAbsenceOfErrors()
                .enterEmail(testUser.getEmail())
                .verifyAbsenceOfErrors()
                .enterPassword(RESTORED_PASSWORD)
                .verifyAbsenceOfErrors()
                .logIn(testUser);
    }

    @After
    public void tearDown() {
        cancelPasswordResetIfNecessary(RESTORED_PASSWORD, testUser, generalPage);
    }
}
