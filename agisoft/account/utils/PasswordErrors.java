package com.agisoft.account.utils;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.cssClass;

public class PasswordErrors {

    public static void verifyShortPassword(SelenideElement passwordField, SelenideElement confirmPasswordField) {
        passwordField
                .shouldHave(cssClass("is-invalid"))
                .shouldHave(attribute("placeholder", "Password is too short"));

        confirmPasswordField.shouldHave(cssClass("is-invalid"));
    }

    public static void verifyDifferentPasswords(SelenideElement passwordField, SelenideElement confirmPasswordField) {
        passwordField
                .shouldHave(cssClass("is-invalid"))
                .shouldHave(attribute("placeholder", "Password doesn't match"));

        confirmPasswordField.shouldHave(cssClass("is-invalid"));
    }

    public static void verifyNotConfirmedPassword(SelenideElement passwordField, SelenideElement confirmPasswordField) {
        confirmPasswordField
                .shouldHave(cssClass("is-invalid"))
                .shouldHave(attribute("placeholder", "Confirm password"));

        passwordField.shouldHave(cssClass("is-invalid"));
    }

    public static void verifyEmptyPassword(SelenideElement passwordField, SelenideElement confirmPasswordField) {
        passwordField
                .shouldHave(cssClass("is-invalid"))
                .shouldHave(attribute("placeholder", "Password can't be empty"));

        confirmPasswordField.shouldHave(cssClass("is-invalid"));
    }
}
