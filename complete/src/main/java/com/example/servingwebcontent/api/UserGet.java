package com.example.servingwebcontent.api;

import com.example.servingwebcontent.apiclasses.User;

import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class UserGet extends Get{
    private String myApiKey;


    public UserGet(String api) {
        this.myApiKey = api;
    }

    @Override
    public User getApi() throws IOException, InterruptedException, ParseException {
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

        System.out.println("Response code: " + response.statusCode()); // Status code of 4xx or 5xx indicates an error with the request or with the server, respectively.
        System.out.println("Full JSON response: " + response.body());

        JSONParser parser = new JSONParser();
        JSONArray json = new JSONArray(response.body());
        System.out.println(json.get(0));
        System.out.println(json.getJSONObject(0).get("id"));


        return null;
    }
}
