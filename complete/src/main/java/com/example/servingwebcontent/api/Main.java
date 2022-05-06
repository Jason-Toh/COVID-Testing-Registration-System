package com.example.servingwebcontent.api;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Delete Testing
        String api = System.getenv("API_KEY");
        String covidTestId = "21818dc2-2384-4063-bc8c-ae047d92d2d9";
        APIfactory testFactory = new CovidTestFactory(api);
        Delete covidTestDelete = testFactory.createDelete();
        covidTestDelete.deleteApi(covidTestId);
    }
}
