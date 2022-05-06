package com.example.servingwebcontent.api;

import com.example.servingwebcontent.models.Booking;
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

public class BookingGet extends Get<Booking> {

    private final String myApiKey;

    public BookingGet(String myApiKey) {
        this.myApiKey = myApiKey;
    }

    @Override
    public Collection<Booking> getApi() throws IOException, InterruptedException, ParseException {
        List<Booking> bookings = new ArrayList<>();
        String rootUrl = "https://fit3077.com/api/v2";
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
            String status = (String) json.getJSONObject(i).get("status");
            JSONObject additionalInfoJSON = (JSONObject) json.getJSONObject(i).get("additionalInfo");

            String url = "";
            String qr = "";

            // Get the URL from the JSON if the attribute exists
            try {
                url = additionalInfoJSON.getString("url");
            } catch (Exception exception) {
                url = "";
            }

            // Get the QR from JSON if the attribute exists
            try {
                qr = additionalInfoJSON.getString("qrCode");
            } catch (Exception exception) {
                qr = "";
            }

            String testingSiteName = null;
            String testingSiteId = null;

            // If the testing site is not a null object, get the name and id of the testing
            // site
            if (!json.getJSONObject(i).get("testingSite").equals(null)) {
                JSONObject testingSite = (JSONObject) json.getJSONObject(i).get("testingSite");
                testingSiteName = testingSite.getString("name");
                testingSiteId = testingSite.getString("id");
            }

            JSONObject customer = (JSONObject) json.getJSONObject(i).get("customer");
            String customerId = customer.getString("id");
            String customerName = customer.getString("givenName") + " " + customer.getString("familyName");

            Booking booking = new Booking(bookingId, customerId, customerName, testingSiteId, testingSiteName,
                    smsPin,
                    startTime, status, url, qr);
            bookings.add(booking);
        }

        return bookings;
    }

}
