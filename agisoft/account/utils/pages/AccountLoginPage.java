package com.agisoft.account.utils.pages;

import com.agisoft.utils.users.User;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class AccountLoginPage extends AccountBasePage {

    public static final String RELATIVE_URL = "/login";

    private SelenideElement emailField = $(byName("username"));
    private SelenideElement passwordField = $(byName("password"));
    private SelenideElement loginButton = $(byText("Log In"));
    private SelenideElement forgotPasswordLink = $(byLinkText("I forgot password"));

    public void waitForPageLoad() {
        $(byText("Log In")).should(exist);
    }

    public String getRelativeUrl() {
        return RELATIVE_URL;
    }

    public AccountLoginPage enterEmail(String email) {
        emailField
                .setValue(email)
                .shouldNotHave(cssClass("is-invalid"));
        return this;
    }

    public AccountLoginPage enterPassword(String password) {
        passwordField
                .setValue(password)
                .shouldNotHave(cssClass("is-invalid"));
        return this;
    }

    //метод, где ожидается ошибка входа
    public AccountLoginPage logIn() {
        loginButton.click();
        return this;
    }

    private boolean hasInputErrors() {
        //ожидание появления ошибки
        sleep(500);
        return emailField.has(cssClass("is-invalid")) || passwordField.has(cssClass("is-invalid"));
    }

    //метод, где ожидается удачный вход
    public GeneralPage logIn(User user) {
        loginButton.click();

        //GeneralPage еще грузится или уже загрузилась
        if (!hasInputErrors() || GeneralPage.hasLoaded()) {
            return new GeneralPage(user);
        }

        return null;
    }

    public ForgotPasswordPage clickOnForgotPassword() {
        forgotPasswordLink.click();
        return new ForgotPasswordPage();
    }

    public AccountLoginPage verifyErrors() {
        verifyPage();

        emailField
                .shouldHave(cssClass("is-invalid"))
                .shouldHave(attribute("placeholder", "Incorrect email"));

        passwordField
                .shouldHave(cssClass("is-invalid"))
                .shouldHave(attribute("placeholder", "Incorrect password"));

        return this;
    }

    public AccountLoginPage verifyAbsenceOfErrors() {
        return (AccountLoginPage) verifyAbsenceOfFieldErrors(emailField, passwordField);
    }
}
