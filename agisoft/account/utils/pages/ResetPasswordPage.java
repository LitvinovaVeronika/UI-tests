package com.agisoft.account.utils.pages;

import com.agisoft.account.utils.PasswordErrors;
import com.agisoft.utils.users.User;
import com.codeborne.selenide.SelenideElement;

import static com.agisoft.account.utils.AccountTestProvision.sendPasswordChangeRequest;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ResetPasswordPage extends AccountBasePage {

    public static final String RELATIVE_URL = "/reset-password";
    private final String link;

    private SelenideElement passwordField = $(byName("password"));
    private SelenideElement confirmPasswordField = $(byName("confirmPassword"));
    private SelenideElement doneButton = $(byText("Done"));
    private SelenideElement logInButton = $(byLinkText("Log In"));
    public SelenideElement successEndTitle = $(byText("Password was changed"));
    public SelenideElement failedEndTitle = $(byText("Error"));
    private SelenideElement logInLink = $(byLinkText("log in"));

    ResetPasswordPage(String link) {
        super();
        this.link = link;
    }

    public void waitForPageLoad() {
        $(byText("Reset password")).should(exist);
    }

    public String getLink() {
        return link;
    }

    public String getRelativeUrl() {
        return RELATIVE_URL;
    }

    public static ResetPasswordPage getResetPasswordPage(User user) throws Exception {
        return
                sendPasswordChangeRequest(user.getEmail())
                .openEmailLink(user.getEmail(), user.getEmailPassword());
    }

    @Override
    public ResetPasswordPage reloadPage() {
        return (ResetPasswordPage) super.reloadPage();
    }

    public ResetPasswordPage enterPassword(String password) {
        passwordField
                .setValue(password)
                .shouldNotHave(cssClass("is-invalid"));
        return this;
    }

    public ResetPasswordPage confirmPassword(String password) {
        confirmPasswordField
                .setValue(password)
                .shouldNotHave(cssClass("is-invalid"));
        return this;
    }

    public ResetPasswordPage verifyAbsenceOfPasswordErrors() {
        return (ResetPasswordPage) verifyAbsenceOfFieldErrors(passwordField, confirmPasswordField);
    }

    public ResetPasswordPage verifyShortPasswordErrors() {
        PasswordErrors.verifyShortPassword(passwordField, confirmPasswordField);
        return this;
    }

    public ResetPasswordPage verifyDifferentPasswordsErrors() {
        PasswordErrors.verifyDifferentPasswords(passwordField, confirmPasswordField);
        return this;
    }

    public ResetPasswordPage verifyNotConfirmedPasswordErrors() {
        PasswordErrors.verifyNotConfirmedPassword(passwordField, confirmPasswordField);
        return this;
    }

    public ResetPasswordPage verifyEmptyPasswordErrors() {
        PasswordErrors.verifyEmptyPassword(passwordField, confirmPasswordField);
        return this;
    }

    public ResetPasswordPage clickOnDone() {
        doneButton.click();
        return this;
    }

    public ResetPasswordPage checkSuccessEndTitle() {
        successEndTitle.should(exist);
        return this;
    }

    public ResetPasswordPage checkFailedEndTitle() {
        failedEndTitle.should(exist);
        return this;
    }

    public AccountLoginPage followTheLogInLink() {
        logInLink.click();
        return new AccountLoginPage();
    }

    public AccountLoginPage logIn() {
        logInButton.click();
        return new AccountLoginPage();
    }
}
