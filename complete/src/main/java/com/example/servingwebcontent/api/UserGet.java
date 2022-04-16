package com.example.servingwebcontent.api;

import com.example.servingwebcontent.apiclasses.User;

import org.json.JSONArray;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserGet extends Get<User> {
    private String myApiKey;
    private List<User> users = new ArrayList<>();

    public UserGet(String api) {
        this.myApiKey = api;
    }

    @Override
    public Collection<User> getApi() throws IOException, InterruptedException {
        String rootUrl = "https://fit3077.com/api/v1";
        String usersUrl = rootUrl + "/user";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(usersUrl))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();

        HttpResponse<String> response;
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Convert String into JSONArray
        JSONArray json = new JSONArray(response.body());
        // Create users list


        // Add user into users list
        for(int i = 0; i < json.length(); i++){
            String id = (String)json.getJSONObject(i).get("id");
            String givenName = (String)json.getJSONObject(i).get("givenName");
            String familyName = (String)json.getJSONObject(i).get("familyName");
            String userName = (String)json.getJSONObject(i).get("userName");
            String phoneNumber = (String)json.getJSONObject(i).get("phoneNumber");
            boolean isCustomer = (boolean)json.getJSONObject(i).get("isCustomer");
            boolean isReceptionist = (boolean)json.getJSONObject(i).get("isReceptionist");
            boolean isHealthcareWorker = (boolean)json.getJSONObject(i).get("isHealthcareWorker");
            User user = new User(id, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker);
            users.add(user);
        }
        return users;
    }
}
