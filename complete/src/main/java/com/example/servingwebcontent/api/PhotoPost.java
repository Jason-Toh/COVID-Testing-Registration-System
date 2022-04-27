package com.example.servingwebcontent.api;

import com.example.servingwebcontent.tool.RandomStringGenerator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PhotoPost extends Post {
        private final String myApiKey;
        private final String qrcodeString;

        public PhotoPost(String myApiKey, String qrcodeString) {
                this.myApiKey = myApiKey;
                this.qrcodeString = qrcodeString;
        }

        @Override
        public String postApi() throws IOException, InterruptedException {
                String rootUrl = "https://fit3077.com/api/v1";

                String jsonString = "{" +
                                "\"additionalInfo\":" + "{ " +
                                "\"qrcodeString\":\"" + qrcodeString + "\""
                                + "}" + "}";
                // Note the POST() method being used here, and the request body is supplied to
                // it.
                // A request body needs to be supplied to this endpoint, otherwise a 400 Bad
                // Request error will be returned.
                String usersVerifyTokenUrl = rootUrl + "/photo";
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder(URI.create(usersVerifyTokenUrl)) // Return a JWT so we can
                                                                                              // use it in Part 5 later.
                                .setHeader("Authorization", myApiKey)
                                .header("Content-Type", "application/json") // This header needs to be set when sending
                                                                            // a JSON request body.
                                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                                .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // System.out.println("photo POST :> \n----");
                // System.out.println(request.uri());
                // System.out.println("Response code: " + response.statusCode());
                // System.out.println("Full JSON response: " + response.body());
                return response.body();
        }
}
