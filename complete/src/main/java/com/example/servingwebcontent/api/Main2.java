package com.example.servingwebcontent.api;

import com.example.servingwebcontent.models.AuthenticateSingleton;

public class Main2 {
    public static void main(String[] args) {
        AuthenticateSingleton instance = AuthenticateSingleton.getInstance();

        System.out.println(instance.getIsUserAuthenticated());

        instance.authenticate("test");

        System.out.println(instance.getIsUserAuthenticated());
    }
}
