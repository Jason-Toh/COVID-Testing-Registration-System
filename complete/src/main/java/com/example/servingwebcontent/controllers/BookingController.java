package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.*;
import com.example.servingwebcontent.enumeration.TestType;
import com.example.servingwebcontent.models.*;
import com.example.servingwebcontent.domain.BookingForm;
import com.example.servingwebcontent.domain.BookingStatusForm;
import com.example.servingwebcontent.tool.RandomPinGenerator;
import com.google.zxing.WriterException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class BookingController {

    AuthenticateSingleton authenticateInstance = AuthenticateSingleton.getInstance();

    public List<TestingSite> getTestingSites() {

        // Get testing-sites and put it into model
        APIfactory testingFactory = new TestingSiteFactory(System.getenv("API_KEY"));
        Get testingSiteGet = testingFactory.createGet();

        List<TestingSite> testingSiteModels = new ArrayList<>();

        try {
            // Testing-site collection
            Collection<TestingSite> testingSites = testingSiteGet.getApi();

            // Testing Sites which allow on site booking and testing
            for (TestingSite testingSite : testingSites) {
                if (testingSite.getAdditonalInfo().isOnSiteBookingAndTesting()) {
                    testingSiteModels.add(testingSite);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return testingSiteModels;
    }

    public List<User> getUsers() {

        // Get the users and put it in a model
        APIfactory userFactory = new UserFactory(System.getenv("API_KEY"));
        Get userGet = userFactory.createGet();

        List<User> userModels = new ArrayList<>();

        try {
            // Users collection
            Collection<User> users = userGet.getApi();
            Iterator<User> iterator = users.iterator();

            while (iterator.hasNext()) {
                userModels.add(iterator.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return userModels;
    }

    public List<String> getTestTypes() {

        // Get test-type and put it into model
        List<String> testTypeModels = new ArrayList<>();
        for (TestType testType : TestType.values()) {
            testTypeModels.add(testType + "");
        }

        return testTypeModels;
    }

    @GetMapping("/register")
    public String getRegister(Model model) {

        // If the user is not authenticated, redirect to login
        if (!authenticateInstance.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        // If the user is not a receptioniset, redirect to notAuthorised
        if (!authenticateInstance.getUser().isReceptionist()) {
            return "notAuthorised";
        }

        BookingForm bookingForm = new BookingForm();
        model.addAttribute("bookingForm", bookingForm);

        List<TestingSite> testingSiteModels = getTestingSites();
        model.addAttribute("testingSiteModels", testingSiteModels);

        List<User> userModels = getUsers();
        model.addAttribute("userModels", userModels);

        List<String> testTypeModels = getTestTypes();
        model.addAttribute("testTypeModels", testTypeModels);

        // 4. Get smsPin
        RandomPinGenerator rad = new RandomPinGenerator();
        String smsPin = rad.getPin();

        return "register";
    }

    private boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    @GetMapping("/bookingStatus")
    public String askForPinCode(Model model) {

        if (!authenticateInstance.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        return "pinVerification";
    }

    @PostMapping("/submitPinCode")
    public String displayBookingStatus(@ModelAttribute("bookingStatusForm") BookingStatusForm bookingStatusForm,
            Model model) {

        APIfactory bookingFactory = new BookingFactory(System.getenv("API_KEY"));

        Get bookingGet = bookingFactory.createGet();

        String pinCode = bookingStatusForm.getPinCode();
        boolean exist = false;

        try {
            Collection<Booking> bookingCollection = bookingGet.getApi();

            for (Booking booking : bookingCollection) {
                // If the pincode exists in any of the booking object
                if (booking.getSmsPin().equals(pinCode)) {
                    model.addAttribute("booking", booking);
                    exist = true;
                    break;
                }
            }

        } catch (Exception exception) {
            System.out.println(exception);
        }

        if (!exist) {
            model.addAttribute("error", "Pin code does not exist");
            return "pinVerification";
        } else {
            return "bookingStatus";
        }

    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("bookingForm") BookingForm bookingForm, Model model)
            throws IOException, InterruptedException, WriterException {

        // Make booking post here
        APIfactory bookingFactory = new BookingFactory(
                System.getenv("API_KEY"), bookingForm.getCustomerUsername(), bookingForm.getTestingSite(),
                bookingForm.getTime());

        Get bookingGet = bookingFactory.createGet();

        try {
            Collection<Booking> bookingCollection = bookingGet.getApi();
            for (Booking booking : bookingCollection) {

                // Some testing sites have null information
                if (booking.getTestingSiteId() == null) {
                    continue;
                }

                // Check if the chose testing site is the same of any of the existing testing
                // site
                if (booking.getTestingSiteId().equals(bookingForm.getTestingSite())) {
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

                    Date bookingFormDate = (Date) formatter.parse(bookingForm.getTime());
                    Date bookingDate = (Date) formatter.parse(booking.getStartTime());

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
                            model.addAttribute("error",
                                    "Booking Time " + startTime + " - " + endTime + " has been taken");

                            BookingForm bookingForm2 = new BookingForm();
                            model.addAttribute("bookingForm", bookingForm2);

                            List<TestingSite> testingSiteModels = getTestingSites();
                            model.addAttribute("testingSiteModels", testingSiteModels);

                            List<User> userModels = getUsers();
                            model.addAttribute("userModels", userModels);

                            List<String> testTypeModels = getTestTypes();
                            model.addAttribute("testTypeModels", testTypeModels);
                            return "register";
                        }
                    }
                }
            }

        } catch (

        Exception exception) {
            System.out.println(exception);
        }

        Post bookingPost = bookingFactory.createPost();
        String jsonPost = bookingPost.postApi();

        // Convert booking return JSON string to JSONObject and get the booking id
        JSONObject book = new JSONObject(jsonPost);
        String bookingId = book.get("id") + "";

        // Post Qr code String
        if (bookingForm.isOnHomeBooking()) {

            String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
            APIfactory apIfactory = new BookingFactory(System.getenv("API_KEY"), bookingId, bookingForm.getQr(), url,
                    "");
            Patch bookingPatch = apIfactory.createPatch();
            String returnValue = bookingPatch.patchApi();
        }

        model.addAttribute("pinCode", book.get("smsPin") + "");

        return "pinCode";
    }

}
