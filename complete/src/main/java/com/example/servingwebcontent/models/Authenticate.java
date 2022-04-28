package com.example.servingwebcontent.models;

import java.util.Collection;

import com.example.servingwebcontent.api.APIfactory;
import com.example.servingwebcontent.api.Get;
import com.example.servingwebcontent.api.UserFactory;

public class Authenticate {
    public static boolean isUserAuthenticated = false;
    public static User user = null;

    public static boolean getIsUserAuthenticated() {
        return isUserAuthenticated;
    }

    public static void authenticate(String username) {
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

    public static void deauthenicate() {
        isUserAuthenticated = false;
        user = null;
    }

    public static User getUser() {
        return user;
    }
}
