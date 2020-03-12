package com.agisoft.account.utils;

import com.agisoft.account.utils.pages.AccountLoginPage;
import com.agisoft.account.utils.pages.ForgotPasswordPage;
import com.agisoft.account.utils.pages.GeneralPage;
import com.agisoft.account.utils.pages.PersonalDataPage;
import com.agisoft.account.utils.pages.ResetPasswordPage;
import com.agisoft.utils.users.User;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;
import static java.util.Objects.nonNull;

public class AccountTestProvision {

    public static boolean cancelPasswordResetIfNecessary(String newPassword, User user, GeneralPage page) {
        if (nonNull(page)) {
            if (nonNull(newPassword)) {
                PersonalDataPage personalDataPage = page
                        .clickOnPersonalData()
                        .enterPassword(newPassword)
                        .clickOnSetNewPassword();

                if (personalDataPage.canPasswordChanged())
                    personalDataPage
                            .enterNewPassword(user.getPassword())
                            .confirmNewPassword(user.getPassword())
                            .clickOnSave()
                            .checkForPasswordUpdate();
            }
            clearBrowserCookies();
            return true;
        }
        return  false;
    }

    public static boolean cancelPasswordResetIfNecessary(String newPassword, User user, ResetPasswordPage page) {

        if (nonNull(page) && (page.successEndTitle.exists() || page.failedEndTitle.exists())) {

            AccountLoginPage loginPage;
            if (page.successEndTitle.exists())
                loginPage = page.followTheLogInLink();
            else
                loginPage = page.logIn();

            GeneralPage generalPage = loginPage
                    .enterEmail(user.getEmail())
                    .enterPassword(newPassword)
                    .logIn(user);

            if (nonNull(generalPage))
                return cancelPasswordResetIfNecessary(newPassword, user, generalPage);
        }

        return false;
    }

    public static AccountLoginPage resetPassword(User user, String newPassword) throws Exception {
        return  ResetPasswordPage.getResetPasswordPage(user)
                .enterPassword(newPassword)
                .confirmPassword(newPassword)
                .clickOnDone()
                .checkSuccessEndTitle()
                .followTheLogInLink();
    }

    public static void changePassword(User user, String newPassword) {
        PersonalDataPage.getPersonalDataPage(user)
                .verifyAbsenceOfCurrentPasswordError()
                .enterPassword(user.getPassword())
                .clickOnSetNewPassword()
                .verifyAbsenceOfNewPasswordErrors()
                .enterNewPassword(newPassword)
                .verifyAbsenceOfNewPasswordErrors()
                .confirmNewPassword(newPassword)
                .verifyAbsenceOfNewPasswordErrors()
                .clickOnSave()
                .checkForPasswordUpdate()
                .logOut();
    }

    public static ForgotPasswordPage sendPasswordChangeRequest(String email) {
        return open(AccountLoginPage.RELATIVE_URL, AccountLoginPage.class)
                .verifyAbsenceOfErrors()
                .clickOnForgotPassword()
                .enterEmail(email)
                .clickOnSubmit()
                .waitUntilDone();
    }
}
