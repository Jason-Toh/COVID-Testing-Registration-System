package com.example.servingwebcontent.api;

import com.example.servingwebcontent.apiclasses.Address;
import com.example.servingwebcontent.apiclasses.Booking;
import com.example.servingwebcontent.apiclasses.TestingSite;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookingGet extends Get {
    private String myApiKey;
    private List<Booking> bookings = new ArrayList<>();

    public BookingGet(String myApiKey) {
        this.myApiKey = myApiKey;
    }

    @Override
    public Collection<Booking> getApi() throws IOException, InterruptedException, ParseException {
        String rootUrl = "https://fit3077.com/api/v1";
        String usersUrl = rootUrl + "/booking";

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

        // Add user into users list
        for (int i = 0; i < json.length(); i++) {
            String bookingId = (String) json.getJSONObject(i).get("id");
            String smsPin = (String) json.getJSONObject(i).get("smsPin");
            String startTime = (String) json.getJSONObject(i).get("startTime");

            String testingSiteName = null;
            String testingSiteId = null;
            if (!json.getJSONObject(i).get("testingSite").equals(null)){
                JSONObject testingSite = (JSONObject) json.getJSONObject(i).get("testingSite");
                testingSiteName = testingSite.getString("name");
                testingSiteId = testingSite.getString("id");
            }


            Booking booking = Booking.getInstance(bookingId, testingSiteId, testingSiteName, smsPin, startTime);
            bookings.add(booking);
        }

        return bookings;
    }

}
