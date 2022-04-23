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

        String api = "NrMhfCkHTjJjzHTWR8z8nP6FjcGg8K";

        // 1.0 Get User and return list of users information
        System.out.println("-----FACTORY CLASS CREATE GET USERS-------");
        APIfactory factory1 = new UserFactory(api);
        Get userGet = factory1.createGet();
        try {
            // users collection
            Collection<User> users = userGet.getApi();

            // Just to show all the user in users
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
//                System.out.println("--> " + iterator.next().toJSONStringFormat("customer"));
                System.out.println("--> " + iterator.next());
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        System.out.println("\n");

        // 1.1 Get Testing-Site and return list testing-sites information
        System.out.println("-----FACTORY CLASS CREATE GET TESTING SITES-------");
        APIfactory factory2 = new TestingSiteFactory(api);
        Get testingSiteGet = factory2.createGet();
        try {
            // users collection
            Collection<TestingSite> testingSites = testingSiteGet.getApi();
            // Just to show all the user in users
            Iterator<TestingSite> iterator = testingSites.iterator();
            while (iterator.hasNext()) {
//                System.out.println("--> " + iterator.next().toJSONStringFormat());
                System.out.println("--> " + iterator.next());
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }

        // 2.0 Post Booking given customer Id, patient Id, start time
        String custId = "b96a725e-4d80-4f82-ba64-0ce7a856ff8e";
        String patientId = "ecc52cc1-a3e4-4037-a80f-62d3799645f4";
        String startTime = "2022-04-18T13:45:01.046652100";

        APIfactory factory3 = new BookingFactory(api,custId,patientId,startTime);
        Post bookingPost = factory3.createPost();
        String jsonPost = bookingPost.postApi();

        Get bookingGet = factory3.createGet();
        Collection<Booking> bookingGet1 = bookingGet.getApi();

        // 2.1 Post CovidTest given testtype, admin id, patient id, booking id(obtain from 2.0), result
        JSONObject book = new JSONObject(jsonPost);

        String bookingId = book.get("id")+"";
        String testType = "PCR";
        String administererId = "70e136be-40ec-4e4b-8682-ac457f43a3cf";
        String result = "POSITIVE";

        APIfactory factory4 = new CovidTestFactory(api, testType, patientId, administererId, bookingId,null);
        Post covidTestPost = factory4.createPost();
        String jsonPost1 = covidTestPost.postApi();

    }
}
