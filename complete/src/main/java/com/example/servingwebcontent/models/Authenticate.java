package com.example.servingwebcontent.models;

public class Authenticate {
    public static boolean isUserAuthenticated = false;

    public static boolean getIsUserAuthenticated() {
        return isUserAuthenticated;
    }

    public static void authenticate() {
        isUserAuthenticated = true;
    }

    public static void deauthenicate() {
        isUserAuthenticated = false;
    }
}
