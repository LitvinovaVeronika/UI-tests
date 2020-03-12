package com.agisoft.account.utils.pages;

import com.agisoft.BasePage;

public abstract class AccountBasePage extends BasePage {

    public static final String BASE_URL = "https://account.agisoft.com";

    AccountBasePage() {
        super();
    }

    protected abstract void waitForPageLoad();

    protected abstract String getRelativeUrl();

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }
}
