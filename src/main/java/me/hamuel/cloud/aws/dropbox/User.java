package me.hamuel.cloud.aws.dropbox;

import org.apache.commons.lang3.StringUtils;

public class User {

    private static String username = null;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static boolean isLoggedIn(){
        return !StringUtils.isBlank(username);
    }
}
