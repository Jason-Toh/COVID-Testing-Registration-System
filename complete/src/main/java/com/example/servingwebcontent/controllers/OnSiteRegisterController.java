package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.*;
import com.example.servingwebcontent.models.Authenticate;
import com.example.servingwebcontent.models.Booking;
import com.example.servingwebcontent.models.TestType;
import com.example.servingwebcontent.models.TestingSite;
import com.example.servingwebcontent.models.User;
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
public class OnSiteRegisterController {

    static boolean isUserAuthenticated = Authenticate.getIsUserAuthenticated();

    @GetMapping("/register")
    public String getRegister(Model model) {

        if (!Authenticate.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        // 1.
        BookingForm bookingForm = new BookingForm();
        model.addAttribute("bookingForm", bookingForm);

        // 2.1 Get testing-sites and put it into model
        // API factory
        APIfactory factory = new TestingSiteFactory(System.getenv("API_KEY"));
        Get testingSiteGet = factory.createGet();

        List<TestingSite> testingSiteModels = new ArrayList<>();

        try {
            // Testing-site collection
            Collection<TestingSite> testingSites = testingSiteGet.getApi();
            Iterator<TestingSite> iterator = testingSites.iterator();
            while (iterator.hasNext()) {
                testingSiteModels.add(iterator.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        model.addAttribute("testingSiteModels", testingSiteModels);
        // 2.2
        APIfactory factory1 = new UserFactory(System.getenv("API_KEY"));
        Get userGet = factory1.createGet();

        List<User> userModels = new ArrayList<>();

        try {
            // Testing-site collection
            Collection<User> users = userGet.getApi();
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
                userModels.add(iterator.next());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        model.addAttribute("userModels", userModels);

        // 3. Get test-type and put it into model
        List<String> testTypeModels = new ArrayList<>();
        for (TestType testType : TestType.values()) {
            testTypeModels.add(testType + "");
        }
        model.addAttribute("testTypeModels", testTypeModels);

        // 4. Get smsPin
        RandomPinGenerator rad = new RandomPinGenerator();
        String smsPin = rad.getPin();
        // model.addAttribute("smsPinModel", smsPinModel);

        return "register";
    }

    private boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    @GetMapping("/bookingStatus")
    public String askForPinCode(Model model) {

        if (!Authenticate.getIsUserAuthenticated()) {
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
                if (booking.getTestingSiteId().equals(bookingForm.getTestingSite())) {
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

                    Date bookingFormDate = (Date) formatter.parse(bookingForm.getTime());
                    Date bookingDate = (Date) formatter.parse(booking.getStartTime());

                    Calendar bookingFormStartTime = Calendar.getInstance();
                    bookingFormStartTime.setTime(bookingFormDate);

                    Calendar bookingStartTime = Calendar.getInstance();
                    bookingStartTime.setTime(bookingDate);

                    // Add 10 minutes interval for each booking
                    Calendar bookingEndTime = Calendar.getInstance();
                    bookingEndTime.setTime(bookingDate);
                    bookingEndTime.add(Calendar.MINUTE, 10);

                    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

                    if (isDateSame(bookingFormStartTime, bookingEndTime)) {
                        String startTime = timeFormatter.format(bookingStartTime.getTime());
                        String endTime = timeFormatter.format(bookingEndTime.getTime());

                        // Bookings can be made every 10 minutes after each booking
                        if (bookingFormStartTime.getTime().after(bookingStartTime.getTime())
                                && bookingFormStartTime.getTime().before(bookingEndTime.getTime())) {
                            model.addAttribute("error",
                                    "Booking Time " + startTime + " - " + endTime + " has been taken");
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
            // Generate random String

            APIfactory factory4 = new PhotoFactory(System.getenv("API_KEY"), bookingForm.getQr());
            Post photoPost = factory4.createPost();
            String jsonPost1 = photoPost.postApi();
        }

        model.addAttribute("pinCode", book.get("smsPin") + "");

        return "pinCode";
    }

}
