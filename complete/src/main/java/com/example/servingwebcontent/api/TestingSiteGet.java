package com.example.servingwebcontent.api;

import com.example.servingwebcontent.apiclasses.Address;
import com.example.servingwebcontent.apiclasses.TestingSite;
import com.example.servingwebcontent.apiclasses.TestingSiteStatus;
import com.example.servingwebcontent.apiclasses.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestingSiteGet extends Get {
    private String myApiKey;
    private List<TestingSite> testingSites = new ArrayList<>();

    public TestingSiteGet(String api) {
        this.myApiKey = api;
    }

    @Override
    public Collection<TestingSite> getApi() throws IOException, InterruptedException {
        String rootUrl = "https://fit3077.com/api/v1";
        String usersUrl = rootUrl + "/testing-site";

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
            String id = (String) json.getJSONObject(i).get("id");
            String name = (String) json.getJSONObject(i).get("name");
            String description = (String) json.getJSONObject(i).get("description");
            String websiteUrl = (String) json.getJSONObject(i).get("websiteUrl");
            String phoneNumber = (String) json.getJSONObject(i).get("phoneNumber");
            String createdAt = (String) json.getJSONObject(i).get("createdAt");
            String updatedAt = (String) json.getJSONObject(i).get("updatedAt");
            // Address
            JSONObject addressJson = (JSONObject) json.getJSONObject(i).get("address");
            double latitude = addressJson.getDouble("latitude");
            double longitude = addressJson.getDouble("longitude");
            String unitNumber = addressJson.getString("unitNumber");
            String street = addressJson.getString("street");
            String suburb = addressJson.getString("suburb");
            String state = addressJson.getString("state");
            String postcode = addressJson.getString("postcode");
            Address address = new Address(latitude, longitude, unitNumber, street, suburb, state, postcode);

            // Additional Info class
            JSONObject additionalInfoJson = (JSONObject) json.getJSONObject(i).get("additionalInfo");
            String typeOfFacility = additionalInfoJson.getString("typeOfFacility");
            boolean onSiteBookingAndTesting = additionalInfoJson.getBoolean("onSiteBookingAndTesting");
            int waitingTimeInMins = additionalInfoJson.getInt("waitingTimeInMins");
            String openingTime = additionalInfoJson.getString("openingTime");
            String closingTime = additionalInfoJson.getString("closingTime");
            String openOrClosed = additionalInfoJson.getString("openOrClosed");

            TestingSiteStatus additionalInfo = new TestingSiteStatus(typeOfFacility, onSiteBookingAndTesting,
                    waitingTimeInMins, openingTime, closingTime, openOrClosed);

            TestingSite testingSite = new TestingSite(id, name, description, websiteUrl, phoneNumber, address,
                    additionalInfo, createdAt, updatedAt);
            testingSites.add(testingSite);
        }
        return testingSites;
    }
}
