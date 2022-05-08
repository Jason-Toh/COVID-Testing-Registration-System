package com.example.servingwebcontent.api;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        // Testing CovidTest DELETE
//        String api = System.getenv("API_KEY");
//        String covidTestId = "21818dc2-2384-4063-bc8c-ae047d92d2d9";
//        APIfactory testFactory = new CovidTestFactory(api);
//        Delete covidTestDelete = testFactory.createDelete();
//        covidTestDelete.deleteApi(covidTestId);

        // Testing Booking DELETE
        String api = System.getenv("API_KEY");
        String covidTestId = "90d5b22e-ce89-42e1-aa01-6b2b09586eaa";
        APIfactory testFactory = new BookingFactory(api);
        Delete bookingDelete = testFactory.createDelete();
        bookingDelete.deleteApi(covidTestId);

        //TODAY
    }
}
