package com.agisoft.account.utils.pages;

import com.agisoft.utils.users.User;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class PersonalDataPage extends GeneralPage {

    public static final String RELATIVE_URL = "/personal-data";
    private static final String TAB_NAME = "Personal Data";

    private SelenideElement passwordField = $(byName("currentPassword"));
    private SelenideElement newPasswordField = $(byName("newPassword"));
    private SelenideElement confirmPasswordField = $(byName("confirmPassword"));
    private SelenideElement setNewPasswordButton = $(byText("Set new password"));
    private SelenideElement saveButton = $(byText("Save"));

    PersonalDataPage(User user) { super(user); }

    @Override
    public String getTabName() {
        return TAB_NAME;
    }

    @Override
    public PersonalDataPage reloadPage() {
        return (PersonalDataPage) super.reloadPage();
    }

    @Override
    public String getRelativeUrl() {
        return RELATIVE_URL;
    }

    @Override
    public PersonalDataPage clickOnPersonalData() {
        personalDataLink.click();
        return this;
    }

    public static PersonalDataPage getPersonalDataPage(User user) {
        return getGeneralPage(user)
                .clickOnPersonalData();
    }

    public boolean canPasswordChanged() {
        if (passwordField.is(visible)) {
            sleep(1000);
        }

        return newPasswordField.is(visible) ||
                passwordField.is(visible) && passwordField.has(not(cssClass("is-invalid")));
    }

    public PersonalDataPage enterPassword(String password) {
        passwordField
                .setValue(password)
                .shouldNotHave(cssClass("is-invalid"));
        return this;
    }

    public PersonalDataPage verifyAbsenceOfCurrentPasswordError() {
        return (PersonalDataPage) verifyAbsenceOfFieldErrors(passwordField);
    }

    public PersonalDataPage verifyAbsenceOfNewPasswordErrors() {
        return (PersonalDataPage) verifyAbsenceOfFieldErrors(newPasswordField, confirmPasswordField);
    }

    public PersonalDataPage clickOnSetNewPassword() {
        setNewPasswordButton.click();
        return this;
    }

    public PersonalDataPage enterNewPassword(String password) {
        newPasswordField.setValue(password);
        return this;
    }

    public PersonalDataPage confirmNewPassword(String password) {
        confirmPasswordField.setValue(password);
        return this;
    }

    public PersonalDataPage clickOnSave() {
        saveButton.click();
        return this;
    }

    public PersonalDataPage checkForPasswordUpdate() {
        passwordField.should(exist);
        return this;
    }
}
