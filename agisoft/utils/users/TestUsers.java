package com.agisoft.utils.users;

import com.agisoft.utils.reader.YamlReader;

import java.util.List;
import java.util.Map;

public class TestUsers {

    private static User emptyUser;
    private static User normalUser;
    private static User anotherUser;

    private TestUsers() {
    }

    public User getEmptyUser() {
        return emptyUser;
    }

    public User getNormalUser() {
        return normalUser;
    }

    public User getAnotherUser() {
        return anotherUser;
    }

    public static TestUsers initializeUsers() throws Exception {

        Map config = new YamlReader().read(System.getProperty("configFilePath"));
        User.tokenRequestParams = (Map<String, String>) config.get("tokenRequestParameters");
        List<Map<String, String>> users = (List<Map<String, String>>) config.get("uiTestUsers");

        emptyUser = new User(users.get(0));
        normalUser = new User(users.get(1));
        anotherUser = new User(users.get(1));

        return new TestUsers();
    }
}
