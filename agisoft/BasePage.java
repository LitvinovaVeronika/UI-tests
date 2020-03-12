package com.agisoft;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Selenide.refresh;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public abstract class BasePage {

    protected BasePage() {
        verifyPage();
    }

    protected abstract void waitForPageLoad();

    protected abstract String getRelativeUrl();

    protected abstract String getBaseUrl();

    public void verifyPage() {
        waitForPageLoad();
        assertThat(isThisPage(), is(true));
    }

    public boolean isThisPage() {
        return WebDriverRunner.url().contains(getBaseUrl() + getRelativeUrl());
    }

    public BasePage reloadPage() {
        refresh();
        waitForPageLoad();
        return this;
    }

    protected BasePage verifyAbsenceOfFieldErrors(SelenideElement ... elements) {
        for (SelenideElement element : elements) {
            element.shouldNotHave(cssClass("is-invalid"));
            element.shouldNotHave(cssClass("error"));
        }
        return this;
    }
}
