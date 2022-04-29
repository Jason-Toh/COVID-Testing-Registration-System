package com.example.servingwebcontent.models;

import java.util.Collection;

import com.example.servingwebcontent.api.APIfactory;
import com.example.servingwebcontent.api.Get;
import com.example.servingwebcontent.api.UserFactory;

public class AuthenticateSingleton {

    private static AuthenticateSingleton instance;
    private boolean isUserAuthenticated = false;
    private User user = null;

    private AuthenticateSingleton() {
    };

    public static AuthenticateSingleton getInstance() {
        if (instance == null) {
            instance = new AuthenticateSingleton();
        }
        return instance;
    }



    public boolean getIsUserAuthenticated() {
        return isUserAuthenticated;
    }

    public void authenticate(String username) {
        isUserAuthenticated = true;

        APIfactory userFactory = new UserFactory(System.getenv("API_KEY"));

        Get userGet = userFactory.createGet();
        try {
            Collection<User> userCollection = userGet.getApi();

            for (User user2 : userCollection) {
                if (user2.getUserName().toLowerCase().equals(username.toLowerCase())) {
                    user = user2;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deauthenicate() {
        isUserAuthenticated = false;
        user = null;
    }

    public User getUser() {
        return user;
    }
}
