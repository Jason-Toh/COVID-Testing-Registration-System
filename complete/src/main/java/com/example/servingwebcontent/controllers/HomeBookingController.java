package com.example.servingwebcontent.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.example.servingwebcontent.api.*;
import com.example.servingwebcontent.domain.BookingForm;
import com.example.servingwebcontent.domain.ScanQRForm;
import com.example.servingwebcontent.models.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeBookingController {

    AuthenticateSingleton authenticateInstance = AuthenticateSingleton.getInstance();

    public List<TestingSite> getTestingSites() {
        // 2.1 Get testing-sites and put it into model
        // API factory
        APIfactory factory = new TestingSiteFactory(System.getenv("API_KEY"));
        Get testingSiteGet = factory.createGet();

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

        return userModels;
    }

    public List<String> getTestTypes() {
        // 3. Get test-type and put it into model
        List<String> testTypeModels = new ArrayList<>();
        for (TestType testType : TestType.values()) {
            testTypeModels.add(testType + "");
        }

        return testTypeModels;
    }

    @GetMapping("/homeBooking")
    public String index(Model model) {

        if (!authenticateInstance.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        // 1.
        BookingForm bookingForm = new BookingForm();
        model.addAttribute("bookingForm", bookingForm);

        List<TestingSite> testingSiteModels = getTestingSites();
        model.addAttribute("testingSiteModels", testingSiteModels);

        List<User> userModels = getUsers();
        model.addAttribute("userModels", userModels);

        List<String> testTypeModels = getTestTypes();
        model.addAttribute("testTypeModels", testTypeModels);

        return "onlineBooking";
    }

    @GetMapping("/scanQRCode")
    public String getScanQRCode(Model model) {

        if (!authenticateInstance.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        if (!authenticateInstance.getUser().isReceptionist()) {
            return "notAuthorised";
        }

        return "scanQR";
    }

    @PostMapping("/scanQRCode")
    public String postScanQRCode(@ModelAttribute("interviewForm") ScanQRForm scanQRForm, Model model)
            throws IOException, InterruptedException {

        APIfactory bookingFactory = new BookingFactory(System.getenv("API_KEY"));

        Get bookingGet = bookingFactory.createGet();

        String qrCode = scanQRForm.getQrCode();

        try {
            Collection<Booking> bookingCollection = bookingGet.getApi();
            for (Booking booking : bookingCollection) {
                if (booking.getStatus().toLowerCase().equals("completed")) {
                    model.addAttribute("error", "Booking has already been completed");
                    return "scanQR";
                }

                if (booking.getQr().equals(qrCode)) {
                    String bookingId = booking.getBookingId();
                    APIfactory bookingFactory1 = new BookingFactory(System.getenv("API_KEY"), bookingId,
                            BookingStatus.COMPLETED);
                    Patch bookingPatch = bookingFactory1.createPatch();
                    String returnValue = bookingPatch.patchApi();
                    return "bookingDone";
                }
            }

        } catch (Exception exception) {
            System.out.println(exception);
        }

        model.addAttribute("error", "Qr Code does not exist");
        // TODO: Need to add patch method to change booking status to completed
        // Patch bookingPost = bookingFactory.createPatch();
        // String returnValue = bookingPost.patchApi();

        return "scanQR";
    }
}
