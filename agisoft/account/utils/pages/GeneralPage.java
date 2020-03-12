package com.agisoft.account.utils.pages;

import com.agisoft.utils.users.User;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class GeneralPage extends AccountBasePage {

    protected final User user;

    public static final String RELATIVE_URL = "/general";
    private static final String TAB_NAME = "General";

    SelenideElement modalDialog = $(byClassName("modal-dialog"));
    SelenideElement personalDataLink = $(byLinkText("Personal Data"));
    private SelenideElement logOutButton = $(byLinkText("Log Out"));

    GeneralPage(User user) {
        super();
        this.user = user;
    }

    @Override
    public GeneralPage reloadPage() {
        return ((GeneralPage) super.reloadPage()).killModalDialogIfNecessary();
    }

    public String getTabName() {
        return TAB_NAME;
    }

    public void waitForPageLoad() {
        $(byCssSelector(".nav-item.active a")).shouldHave(text(getTabName()));
    }

    public String getRelativeUrl() {
        return RELATIVE_URL;
    }

    static boolean hasLoaded() {
        return $(byLinkText("Log Out")).exists();
    }

    static GeneralPage getGeneralPage(User user) {
        return open(AccountLoginPage.RELATIVE_URL, AccountLoginPage.class)
                .enterEmail(user.getEmail())
                .enterPassword(user.getPassword())
                .logIn(user);
    }

    public GeneralPage killModalDialogIfNecessary() {
        if (modalDialog.exists()) {
            closeModalDialog();
        }
        return this;
    }

    public GeneralPage closeModalDialog() {
        //такое большое количество проверок на enabled необходимо для того, чтобы потянуть время,
        // иначе selenium не попадает по крестику
        modalDialog.shouldBe(enabled);
        modalDialog.find(byClassName("modal-close"))
                .shouldBe(enabled)
                .click();
        modalDialog.shouldNot(exist);
        return this;
    }

    public PersonalDataPage clickOnPersonalData() {
        personalDataLink.click();
        return new PersonalDataPage(user);
    }

    public AccountLoginPage logOut() {
        killModalDialogIfNecessary();
        logOutButton.click();
        return page(AccountLoginPage.class);
    }
}
