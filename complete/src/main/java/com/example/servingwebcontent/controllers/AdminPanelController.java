package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.*;
// import com.example.servingwebcontent.enumeration.TestType;
import com.example.servingwebcontent.enumeration.BookingStatus;
import com.example.servingwebcontent.models.AuthenticateSingleton;
import com.example.servingwebcontent.models.Booking;
import com.example.servingwebcontent.models.TestingSite;
import com.example.servingwebcontent.models.User;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class AdminPanelController {
    AuthenticateSingleton authenticateInstance = AuthenticateSingleton.getInstance();

    public List<Booking> getBookingListUsingTestingSite() throws InterruptedException, ParseException, IOException {
        String api = System.getenv("API_KEY");
        APIfactory<TestingSite> factory = new TestingSiteFactory(api);
        Get<TestingSite> testingSiteGet = factory.createGet();
        Collection<TestingSite> testingSites = testingSiteGet.getApi();

        // TO DO SEARCH FOR TESTING SITE WHICH THAT ADMIN RESPONSIBLE
        User user = authenticateInstance.getUser();
        for (TestingSite testingSite : testingSites) {
            if (testingSite.getId().equals(user.getTestingSiteId())) {
                return testingSite.getBookings();
            }
        }
        return null;
    }

    public List<TestingSite> getTestingSiteList() {
        List<TestingSite> testingSiteList = new ArrayList<>();

        APIfactory<TestingSite> testingSiteFactory = new TestingSiteFactory(System.getenv("API_KEY"));
        Get<TestingSite> testingSiteGet = testingSiteFactory.createGet();

        try {
            // Testing-site collection
            Collection<TestingSite> testingSiteCollection = testingSiteGet.getApi();

            for (TestingSite testingSite : testingSiteCollection) {
                testingSiteList.add(testingSite);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return testingSiteList;
    }

    @GetMapping("/adminPanel")
    public String getAdmin(Model model) throws InterruptedException, ParseException, IOException {
        if (!authenticateInstance.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        if (!authenticateInstance.getUser().isReceptionist()) {
            return "notAuthorised";
        }

        List<Booking> bookings = getBookingListUsingTestingSite();

        model.addAttribute("bookings", bookings);

        List<TestingSite> testingSiteList = getTestingSiteList();
        model.addAttribute("testingSiteList", testingSiteList);

        return "adminPanel";
    }

    @RequestMapping("/adminPanelDelete/{id}")
    public String showTestingSite(@PathVariable String id, Model model)
            throws IOException, InterruptedException, ParseException {

        APIfactory<Booking> apiFactory = new BookingFactory(System.getenv("API_KEY"));
        Delete deleteBooking = apiFactory.createDelete();
        deleteBooking.deleteApi(id);

        List<Booking> bookings = getBookingListUsingTestingSite();
        model.addAttribute("bookings", bookings);

        List<TestingSite> testingSiteList = getTestingSiteList();
        System.out.println(testingSiteList);
        model.addAttribute("testingSiteList", testingSiteList);

        return "adminPanel";
    }

    @RequestMapping("/adminPanelCancel/{id}/status/{status}")
    public String modifiedStatus(@PathVariable String id, @PathVariable String status, Model model)
            throws IOException, InterruptedException, ParseException {
        System.out.println("iop  "+id+"  uiy  "+ status);

        String api = System.getenv("API_KEY");

        APIfactory<Booking> bookingFactory = new BookingFactory(api, id, BookingStatus.CANCELLED);
        Patch bookingPatch = bookingFactory.createPatch();

        List<String> thingsToPatch = new ArrayList<>();
        thingsToPatch.add("STATUS");
        thingsToPatch.add("ADMIN");

        bookingPatch.patchApi(thingsToPatch);


        List<Booking> bookings = getBookingListUsingTestingSite();
        model.addAttribute("bookings", bookings);

        List<TestingSite> testingSiteList = getTestingSiteList();
        System.out.println(testingSiteList);
        model.addAttribute("testingSiteList", testingSiteList);

        return "adminPanel";
    }

    @RequestMapping(("/adminPanelModified/{id}/testing-site/{second}"))
    public String modifiedTestingSite(@PathVariable String id, @PathVariable String second, Model model)
            throws IOException, InterruptedException, ParseException {

        System.out.println("booking id: " + id + " testing-site id  " + second);

        List<Booking> bookings = getBookingListUsingTestingSite();
        model.addAttribute("bookings", bookings);

        List<TestingSite> testingSiteList = getTestingSiteList();
        model.addAttribute("testingSiteList", testingSiteList);

        return "adminPanel";
    }

    @RequestMapping(("/adminPanelModified/{id}/testing-site/{testsiteid}/bookingTime/{time}"))
    public String modifiedDateTime(@PathVariable String id, @PathVariable String testsiteid, @PathVariable String time,
            Model model)
            throws IOException, InterruptedException, ParseException {

        System.out.println("booking id: " + id + " date-time:  " + time + "" + " testsiteid:  " + testsiteid + "");

        String api = System.getenv("API_KEY");
        if (!testsiteid.equals("testsiteID")) {
            APIfactory<Booking> bookingFactory = new BookingFactory(api, id, null, testsiteid, null);
            Patch bookingPatch = bookingFactory.createPatch();

            List<String> thingsToPatch = new ArrayList<>();
            thingsToPatch.add("TESTSITE");
            thingsToPatch.add("ADMIN");

            bookingPatch.patchApi(thingsToPatch);

        }
        if (!time.equals("date")) {
            APIfactory<Booking> bookingFactory = new BookingFactory(api, id, null, null, time);
            Patch bookingPatch = bookingFactory.createPatch();

            List<String> thingsToPatch = new ArrayList<>();
            thingsToPatch.add("TIME");
            thingsToPatch.add("ADMIN");

            bookingPatch.patchApi(thingsToPatch);

        }

        List<Booking> bookings = getBookingListUsingTestingSite();
        model.addAttribute("bookings", bookings);

        List<TestingSite> testingSiteList = getTestingSiteList();
        model.addAttribute("testingSiteList", testingSiteList);

        return "adminPanel";
    }
}