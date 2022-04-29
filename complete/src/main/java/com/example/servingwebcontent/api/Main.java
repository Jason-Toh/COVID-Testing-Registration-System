package com.example.servingwebcontent.api;

import com.example.servingwebcontent.enumeration.BookingStatus;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        String api = System.getenv("API_KEY");
        String symptom = "no";
        String bookingId = "e5d391d1-8d3e-41ed-b94b-3602649c8d86";

        APIfactory apIfactory = new BookingFactory(api, bookingId, symptom, BookingStatus.COMPLETED);
        Patch bookingPatch = apIfactory.createPatch();
        String returnValue = bookingPatch.patchApi();

    }
}
