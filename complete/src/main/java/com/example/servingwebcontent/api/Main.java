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
        String covidTestId = "5b062fa2-56d3-40c4-8331-aff5e69af2f5";
        APIfactory testFactory = new BookingFactory(api);
        Delete bookingDelete = testFactory.createDelete();
        bookingDelete.deleteApi(covidTestId);

        //TODAY
    }
}
