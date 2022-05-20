package com.example.servingwebcontent.controllers;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.example.servingwebcontent.api.*;
import com.example.servingwebcontent.models.*;
import com.example.servingwebcontent.api.APIfactory;
import com.example.servingwebcontent.api.TestingSiteFactory;
import com.example.servingwebcontent.domain.BookingForm;
import com.example.servingwebcontent.enumeration.TestType;
import com.example.servingwebcontent.models.AuthenticateSingleton;
import com.google.zxing.WriterException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ModifyBookingController {

    AuthenticateSingleton authenticateInstance = AuthenticateSingleton.getInstance();

    public List<TestingSite> getTestingSiteList() {

        // Get testing-sites and put it into model
        APIfactory<TestingSite> testingFactory = new TestingSiteFactory(System.getenv("API_KEY"));
        Get<TestingSite> testingSiteGet = testingFactory.createGet();

        List<TestingSite> testingSiteList = new ArrayList<>();

        try {
            // Testing-site collection
            Collection<TestingSite> testingSiteCollection = testingSiteGet.getApi();

            // Testing Sites which allow on site booking and testing
            for (TestingSite testingSite : testingSiteCollection) {
                if (testingSite.getAdditonalInfo().isOnSiteBookingAndTesting()) {
                    testingSiteList.add(testingSite);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return testingSiteList;
    }

    public List<User> getUserList() {

        // Get the users and put it in a model
        APIfactory<User> userFactory = new UserFactory(System.getenv("API_KEY"));
        Get<User> userGet = userFactory.createGet();

        List<User> userList = new ArrayList<>();

        try {
            // Users collection
            Collection<User> userCollection = userGet.getApi();

            for (User user : userCollection) {
                if (user.isCustomer()) {
                    userList.add(user);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return userList;
    }

    public List<String> getTestTypeList() {

        // Get test-type and put it into model
        List<String> testTypeList = new ArrayList<>();

        for (TestType testType : TestType.values()) {
            testTypeList.add(testType + "");
        }

        return testTypeList;
    }

    public List<Booking> getBookingList() {

        APIfactory<Booking> bookingFactory = new BookingFactory(System.getenv("API_KEY"));
        Get<Booking> bookingGet = bookingFactory.createGet();

        List<Booking> bookingList = new ArrayList<>();

        try {
            Collection<Booking> bookingCollection = bookingGet.getApi();

            for (Booking booking : bookingCollection) {
                bookingList.add(booking);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return bookingList;
    }

    @GetMapping("/profile")
    public String displayProfilePage(Model model) {

        // If the user is not authenticated, redirect to login
        if (!authenticateInstance.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        // If the user is not a customer, redirect to notAuthorised
        if (!authenticateInstance.getUser().isCustomer()) {
            return "notAuthorised";
        }

        List<Booking> bookingList = getBookingList();

        List<Booking> customerBookingList = new ArrayList<>();

        User currentUser = authenticateInstance.getUser();

        for (Booking booking : bookingList) {
            if (booking.getCustomerId().equals(currentUser.getId())) {
                customerBookingList.add(booking);
            }
        }

        model.addAttribute("bookingList", customerBookingList);

        return "profile";
    }

    @RequestMapping("modify/{id}")
    public String modifyBooking(@PathVariable String id, Model model) {

        List<Booking> bookingList = getBookingList();
        List<TestingSite> testingSiteList = getTestingSiteList();

        for (Booking booking : bookingList) {
            if (booking.getBookingId().equals(id)) {
                model.addAttribute("booking", booking);
                String currentDateTime = booking.getStartTime().substring(0, 16);
                model.addAttribute("currentDateTime", currentDateTime);
                break;
            }
        }

        model.addAttribute("testingSiteList", testingSiteList);

        return "modifyBooking";
    }

    private boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    @PostMapping("/modify")
    public String submitModifiedBooking(@ModelAttribute("bookingForm") BookingForm bookingForm, Model model)
            throws IOException, InterruptedException, WriterException, ParseException {

        List<Booking> bookingList = getBookingList();

        boolean check = false;
        boolean check2 = false;

        Booking currentBooking = null;

        List<TestingSite> testingSiteList = getTestingSiteList();
        model.addAttribute("testingSiteList", testingSiteList);

        for (Booking booking : bookingList) {
            if (booking.getBookingId().equals(bookingForm.getBookingID())) {

                if (booking.getTestingSiteId().equals(bookingForm.getTestingSite())) {
                    check = true;
                }

                String currentDateTime = booking.getStartTime().substring(0, 16);

                if (currentDateTime.equals(bookingForm.getTime())) {
                    check2 = true;
                }

                model.addAttribute("booking", booking);
                model.addAttribute("currentDateTime", currentDateTime);

                currentBooking = booking;

                break;
            }
        }

        if (check && check2) {

            model.addAttribute("error", "No changes have been made");

            return "modifyBooking";
        }

        // Check if the chose testing site is the same of any of the existing testing
        // site
        if (currentBooking.getTestingSiteId().equals(bookingForm.getTestingSite())) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

            Date bookingFormDate = (Date) formatter.parse(bookingForm.getTime());
            Date bookingDate = (Date) formatter.parse(currentBooking.getStartTime());

            // Start time of the chosen booking timeslot
            Calendar bookingFormStartTime = Calendar.getInstance();
            bookingFormStartTime.setTime(bookingFormDate);

            // Start time of the existing booking timeslot
            Calendar bookingStartTime = Calendar.getInstance();
            bookingStartTime.setTime(bookingDate);

            // Each timeslot is 10 minutes (10am to 10:05 am)
            // Add 10 minutes interval for each booking
            Calendar bookingEndTime = Calendar.getInstance();
            bookingEndTime.setTime(bookingDate);
            bookingEndTime.add(Calendar.MINUTE, 10);

            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

            // If the booking date is the same as the existing booking timeslot
            if (isDateSame(bookingFormStartTime, bookingEndTime)) {
                String startTime = timeFormatter.format(bookingStartTime.getTime());
                String endTime = timeFormatter.format(bookingEndTime.getTime());

                // Bookings can be made every 10 minutes after each booking
                if (bookingFormStartTime.getTime().equals(bookingStartTime.getTime())
                        || bookingFormStartTime.getTime().after(bookingStartTime.getTime())
                                && bookingFormStartTime.getTime().before(bookingEndTime.getTime())
                        || bookingFormStartTime.getTime().equals(bookingEndTime.getTime())) {
                    model.addAttribute("error2",
                            "Booking Time " + startTime + " - " + endTime + " has been taken");

                    return "modifyBooking";
                }
            }
        }

        // Timestamp of current changes
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime timeNow = LocalDateTime.now();
        String formattedDateTime = dtf.format(timeNow);

        String oldTimestamp = "";
        String oldTestingSiteId = currentBooking.getTestingSiteId();
        String oldTestingSiteName = currentBooking.getTestingSiteName();
        String oldStartTime = currentBooking.getStartTime();

        if (currentBooking.getModifiedTimestamp().isEmpty()) {
            oldTimestamp = currentBooking.getCreatedAt();
        } else {
            oldTimestamp = currentBooking.getModifiedTimestamp();
        }

        PastBooking pastBooking = new PastBooking(oldTimestamp, oldTestingSiteId, oldTestingSiteName, oldStartTime);

        List<PastBooking> pastBookings = currentBooking.getPastBookings();

        if (pastBookings.size() < 3) {
            pastBookings.add(pastBooking);
        } else {
            pastBookings.remove(0);
            pastBookings.add(pastBooking);
        }

        // Patch the changes
        String api = System.getenv("API_KEY");
        APIfactory<Booking> bookingFactory = new BookingFactory(api,
                bookingForm.getBookingID(), null, bookingForm.getTestingSite(), bookingForm.getTime(),
                formattedDateTime, pastBookings);
        Patch bookingPatch = bookingFactory.createPatch();

        List<String> thingsToPatch = new ArrayList<>();

        if (!check) {
            thingsToPatch.add("TESTSITE");
        }

        if (!check2) {
            thingsToPatch.add("TIME");
        }

        bookingPatch.patchApi(thingsToPatch);

        return "modifyDone";
    }

    @RequestMapping("cancel/{id}")
    public String cancelBooking(@PathVariable String id, Model model) throws IOException, InterruptedException {

        // Patch the changes
        String api = System.getenv("API_KEY");
        APIfactory<Booking> bookingFactory = new BookingFactory(api, id, true);
        Patch bookingPatch = bookingFactory.createPatch();

        List<String> thingsToPatch = new ArrayList<>();

        thingsToPatch.add("CANCEL");

        bookingPatch.patchApi(thingsToPatch);

        return "cancelDone";
    }

    @RequestMapping("revert/{id}")
    public String revertBooking(@PathVariable String id, Model model) {

        List<Booking> bookingList = getBookingList();
        List<TestingSite> testingSiteList = getTestingSiteList();

        for (Booking booking : bookingList) {
            if (booking.getBookingId().equals(id)) {
                model.addAttribute("pastBookings", booking.getPastBookings());
                break;
            }
        }

        model.addAttribute("testingSiteList", testingSiteList);

        return "revertBooking";
    }

}
