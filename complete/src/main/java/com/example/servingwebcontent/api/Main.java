package com.example.servingwebcontent.api;

import com.example.servingwebcontent.apiclasses.Booking;
import com.example.servingwebcontent.apiclasses.TestingSite;
import com.example.servingwebcontent.apiclasses.User;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        String api = System.getenv("API_KEY");
        String symptom = "no";
        String bookingId = "e5d391d1-8d3e-41ed-b94b-3602649c8d86";

        APIfactory apIfactory = new BookingFactory(api, bookingId, symptom);
        Patch bookingPatch = apIfactory.createPatch();
        String returnValue = bookingPatch.patchApi();

    }
}
