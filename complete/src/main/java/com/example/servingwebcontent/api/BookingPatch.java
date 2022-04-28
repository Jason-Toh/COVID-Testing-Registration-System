package com.example.servingwebcontent.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BookingPatch extends Patch{
    private String myApiKey;
    private String bookingId;
    private String symptom;

    public BookingPatch(String myApiKey, String bookingId, String symptom) {
        this.myApiKey = myApiKey;
        this.bookingId = bookingId;
        this.symptom = symptom;
    }

    @Override
    public String patchApi() throws IOException, InterruptedException {


        HttpClient client = HttpClient.newHttpClient();
        String rootUrl = "https://fit3077.com/api/v1";
        String usersUrl = rootUrl + "/booking/" + bookingId;

        String jsonString = "{" +
                "\"additionalInfo\":" + "{ " +
                "\"symptom\":\"" + symptom + "\""
                + "}" + "}";

        HttpRequest.BodyPublisher jsonPayload = HttpRequest.BodyPublishers.ofString(jsonString);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(usersUrl))
                .method("PATCH", jsonPayload)
                .setHeader("Authorization", myApiKey)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Booking Patch :> \n----");
        // System.out.println(request.uri());
        System.out.println("Response code: " + response.statusCode());
        System.out.println("Full JSON response: " + response.body());
        return response.body();
    }
}
