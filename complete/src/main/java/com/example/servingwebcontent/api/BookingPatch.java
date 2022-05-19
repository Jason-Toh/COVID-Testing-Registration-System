package com.example.servingwebcontent.api;

import com.example.servingwebcontent.enumeration.BookingStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class BookingPatch extends Patch {
    private final String myApiKey;
    private final String bookingId;
    private final String symptom;
    private final BookingStatus bookingStatus;
    private final String qrCode;
    private final String url;
    private final String testingSiteId;
    private final boolean testingDone;
    private final String startTime;
    private final boolean cancelBooking;
    private final String modifiedTimestamp;
    private final String oldTimestamp;
    private final String oldTestingSiteId;
    private final String oldStartTime;

    public BookingPatch(String myApiKey, String bookingId, String symptom, BookingStatus bookingStatus, String qrCode,
            String url, String testingSiteId, boolean testingDone, String startTime, boolean cancelBooking,
            String modifiedTimestamp, String oldTimestamp, String oldTestingSiteId, String oldStartTime) {
        this.myApiKey = myApiKey;
        this.bookingId = bookingId;
        this.symptom = symptom;
        this.bookingStatus = bookingStatus;
        this.qrCode = qrCode;
        this.url = url;
        this.testingSiteId = testingSiteId;
        this.testingDone = testingDone;
        this.startTime = startTime;
        this.cancelBooking = cancelBooking;
        this.modifiedTimestamp = modifiedTimestamp;
        this.oldTimestamp = oldTimestamp;
        this.oldTestingSiteId = oldTestingSiteId;
        this.oldStartTime = oldStartTime;
    }

    @Override
    public String patchApi(List<String> thingsToPatch) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String rootUrl = "https://fit3077.com/api/v2";
        String usersUrl = rootUrl + "/booking/" + bookingId;
        String jsonString;
        if (thingsToPatch.contains("QR") && thingsToPatch.contains("URL")) {
            jsonString = "{" +
                    "\"additionalInfo\":" + "{ " +
                    "\"qrCode\":\"" + qrCode + "\"," +
                    "\"url\":\"" + url + "\"" +
                    "}" + "}";
        } else if (thingsToPatch.contains("TESTDONE")) {
            jsonString = "{" +
                    "\"additionalInfo\":" + "{ " +
                    "\"testingDone\":\"" + testingDone + "\"" +
                    "}" + "}";
        } else if (thingsToPatch.contains("STATUS")) {
            jsonString = "{" +
                    "\"status\":\"" + bookingStatus + "\"" +
                    "}";
        } else if (thingsToPatch.contains("TESTSITE")) {
            jsonString = "{" +
                    "\"testingSiteId\":\"" + testingSiteId + "\"," +
                    "\"additionalInfo\":" + "{ " +
                    "\"modifiedTimestamp\":\"" + modifiedTimestamp + "\"," +
                    "\"pastBookings\":" + "[" + "{" +
                    "\"timestamp\":\"" + oldTimestamp + "\"," +
                    "\"testingSiteId\":\"" + oldTestingSiteId + "\"," +
                    "\"startTime\":\"" + oldStartTime + "\"" +
                    "}" + "]" +
                    "}" + "}";
        } else if (thingsToPatch.contains("TIME")) {
            jsonString = "{" +
                    "\"startTime\":\"" + startTime + "\"," +
                    "\"additionalInfo\":" + "{ " +
                    "\"modifiedTimestamp\":\"" + modifiedTimestamp + "\"," +
                    "\"pastBookings\":" + "[" + "{" +
                    "\"timestamp\":\"" + oldTimestamp + "\"," +
                    "\"testingSiteId\":\"" + oldTestingSiteId + "\"," +
                    "\"startTime\":\"" + oldStartTime + "\"" +
                    "}" + "]" +
                    "}" + "}";
        } else if (thingsToPatch.contains("CANCEL")) {
            jsonString = "{" +
                    "\"additionalInfo\":" + "{ " +
                    "\"cancelBooking\":\"" + cancelBooking + "\"" +
                    "}" + "}";
        } else {
            jsonString = "{" +
                    "\"status\":\"" + bookingStatus + "\"," +
                    "\"additionalInfo\":" + "{ " +
                    "\"symptom\":\"" + symptom + "\"" +
                    "}" + "}";
        }

        HttpRequest.BodyPublisher jsonPayload = HttpRequest.BodyPublishers.ofString(jsonString);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(usersUrl))
                .method("PATCH", jsonPayload)
                .setHeader("Authorization", myApiKey)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Booking Patch :> \n----");
        System.out.println(request.uri());
        System.out.println("Response code: " + response.statusCode());
        System.out.println("Full JSON response: " + response.body());
        return response.body();
    }
}
