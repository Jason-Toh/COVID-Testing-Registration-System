package com.example.servingwebcontent.api;

import com.example.servingwebcontent.enumeration.BookingStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BookingPatch extends Patch {
    private final String myApiKey;
    private final String bookingId;
    private final String symptom;
    private final BookingStatus bookingStatus;
    private final String qrCode;
    private final String url;

    public BookingPatch(String myApiKey, String bookingId, String symptom, BookingStatus bookingStatus, String qrCode,
            String url) {
        this.myApiKey = myApiKey;
        this.bookingId = bookingId;
        this.symptom = symptom;
        this.bookingStatus = bookingStatus;
        this.qrCode = qrCode;
        this.url = url;
    }

    @Override
    public String patchApi() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String rootUrl = "https://fit3077.com/api/v2";
        String usersUrl = rootUrl + "/booking/" + bookingId;
        String jsonString;
        if (qrCode != null || url != null) {
            jsonString = "{" +
                    "\"additionalInfo\":" + "{ " +
                    "\"qrCode\":\"" + qrCode + "\"," +
                    "\"url\":\"" + url + "\"," +
                    "\"symptom\":\"" + symptom + "\""
                    + "}" + "}";
        } else if (this.bookingStatus != null) {
            jsonString = "{" +
                    "\"status\":\"" + bookingStatus + "\"" +
                    "}";
        } else {
            jsonString = "{" +
                    "\"status\":\"" + bookingStatus + "\"," +
                    "\"additionalInfo\":" + "{ " +
                    "\"symptom\":\"" + symptom + "\""
                    + "}" + "}";
        }

        HttpRequest.BodyPublisher jsonPayload = HttpRequest.BodyPublishers.ofString(jsonString);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(usersUrl))
                .method("PATCH", jsonPayload)
                .setHeader("Authorization", myApiKey)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // System.out.println("Booking Patch :> \n----");
        // System.out.println(request.uri());
        // System.out.println("Response code: " + response.statusCode());
        // System.out.println("Full JSON response: " + response.body());
        return response.body();
    }
}
