package com.agisoft.account;

import com.agisoft.account.utils.pages.AccountBasePage;
import com.agisoft.utils.users.TestUsers;
import com.codeborne.selenide.Configuration;
import org.junit.BeforeClass;

public class AccountFunctionalTest {

    public static TestUsers users;

    @BeforeClass
    public static void configure() throws Exception {
        users = TestUsers.initializeUsers();

        Configuration.baseUrl = AccountBasePage.BASE_URL;
        Configuration.timeout = 5000;
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browser = "chrome";
    }
}
