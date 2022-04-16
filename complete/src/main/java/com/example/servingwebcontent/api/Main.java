package com.example.servingwebcontent.api;

import com.example.servingwebcontent.apiclasses.User;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("hey man");

        APIfactory factory = new UserFactory("NrMhfCkHTjJjzHTWR8z8nP6FjcGg8K");
        Get userGet = factory.createGet();
        try {
            User user = userGet.getApi();
        }
        catch(Exception e) {
            System.out.println(e);
        }

    }
}
