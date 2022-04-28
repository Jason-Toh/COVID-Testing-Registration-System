package com.example.servingwebcontent.api;

import com.example.servingwebcontent.apiclasses.Booking;
import com.example.servingwebcontent.apiclasses.TestingSite;
import com.example.servingwebcontent.apiclasses.User;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {

        String api = System.getenv("API_KEY");
        System.out.println(api);
        APIfactory factory2 = new BookingFactory(System.getenv("API_KEY"));
        Get bookingGet = factory2.createGet();
        Collection jsonGet = bookingGet.getApi();

        System.out.println(jsonGet);

    }
}
