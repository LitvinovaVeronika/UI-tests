package com.agisoft.utils.email;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class EmailCreator {

    //The maximum total length of a user name or other local-part is 64 characters
    //The maximum total length of a domain name or number is 255 characters

    private static final String EMAIL_MASK = "+TEST@geoscan.aero.qa";
    private static final int MAX_LENGTH_RANDOM_EMAIL_PART = 64 - 5;

    public static String getRandomEmail() {

        int randomEmailPartLength = new Random().nextInt(MAX_LENGTH_RANDOM_EMAIL_PART) + 1;

        String generatedString = RandomStringUtils.randomAlphanumeric(randomEmailPartLength);

        return generatedString + EMAIL_MASK;
    }

}
