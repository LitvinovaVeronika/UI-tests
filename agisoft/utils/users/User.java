package com.agisoft.utils.users;

import java.util.Map;

public class User {

    private final String firstName;
    private final String lastName;
    private String email;
    private String password;
    private String emailPassword;

    static Map<String, String> tokenRequestParams;

    public User(
            String firstName, String lastName, String email,
            String password, String emailPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.emailPassword = emailPassword;
    }

    public User(Map<String, String> user) {
        this(
                user.get("firstName"),
                user.get("lastName"),
                user.get("email"),
                user.get("password"),
                user.get("emailPassword"));
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailPassword() {
        return emailPassword;
    }
}
