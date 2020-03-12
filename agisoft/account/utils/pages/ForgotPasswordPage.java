package com.agisoft.account.utils.pages;

import com.agisoft.utils.email.PasswordResetConfirmationEmail;
import com.codeborne.selenide.SelenideElement;

import java.util.Date;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ForgotPasswordPage extends AccountBasePage {

    public static final String RELATIVE_URL = "/forgot-password";
    private Date passwordRequestDate;

    private SelenideElement emailField = $(byName("email"));
    private SelenideElement submitButton = $(byText("Submit"));
    private SelenideElement tryAgainButton = $(byText("Try again!"));
    private SelenideElement headerDone = $(byText("Done"));
    private SelenideElement headerOops = $(byText("Oops"));
    private SelenideElement noSuchEmailText = $(byText("We got no such e-mail in our database. Maybe you should try again"));

    public void waitForPageLoad() {
        $(byText("Restore password")).should(exist);
    }

    public String getRelativeUrl() {
        return RELATIVE_URL;
    }

    public static ForgotPasswordPage getForgotPasswordPage() {
        return open(AccountLoginPage.RELATIVE_URL, AccountLoginPage.class)
                .verifyAbsenceOfErrors()
                .clickOnForgotPassword();
    }

    public ForgotPasswordPage verifyNoSuchEmailError() {
        headerOops.should(exist);
        noSuchEmailText.should(exist);
        return this;
    }

    public ForgotPasswordPage enterEmail(String email) {
        emailField.setValue(email);
        return this;
    }

    public ForgotPasswordPage clickOnSubmit() {
        submitButton.click();
        return this;
    }

    public ForgotPasswordPage clickOnTryAgain() {
        tryAgainButton.click();
        return this;
    }

    public ForgotPasswordPage waitUntilDone() {
        passwordRequestDate = new Date();
        headerDone.should(exist);
        return this;
    }

    ResetPasswordPage openEmailLink (String email, String password) throws Exception {
        String link = PasswordResetConfirmationEmail.getEmailLink(email, password, passwordRequestDate);
        assertThat(link, is(notNullValue()));
        open(link);
        return new ResetPasswordPage(link);
    }
}
