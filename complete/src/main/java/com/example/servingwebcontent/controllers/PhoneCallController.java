package com.example.servingwebcontent.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.servingwebcontent.api.APIfactory;
import com.example.servingwebcontent.api.BookingFactory;
import com.example.servingwebcontent.api.Get;
import com.example.servingwebcontent.api.TestingSiteFactory;
import com.example.servingwebcontent.domain.PhoneCallForm;
import com.example.servingwebcontent.models.AuthenticateSingleton;
import com.example.servingwebcontent.models.Booking;
import com.example.servingwebcontent.models.TestingSite;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PhoneCallController {

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

    @GetMapping("/phonecall")
    public String displayBookingPINPage(Model model) {

        if (!authenticateInstance.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        if (!authenticateInstance.getUser().isReceptionist()) {
            return "notAuthorised";
        }

        return "bookingPIN";
    }

    @PostMapping("/verifyBookingId")
    public String postVerifyBookingId(@ModelAttribute("phoneCallForm") PhoneCallForm phoneCallForm, Model model) {

        List<Booking> bookingList = getBookingList();
        List<TestingSite> testingSiteList = getTestingSiteList();

        boolean check = false;

        for (Booking booking : bookingList) {
            if (booking.getBookingId().equals(phoneCallForm.getBookingId())) {

                if (booking.getTestingDone() == true) {
                    model.addAttribute("error", "Invalid Booking. Covid Test has performed");
                    return "bookingPIN";
                }

                if (booking.getCancelBooking() == true) {
                    model.addAttribute("error", "Invalid Booking. Booking is cancelled");
                    return "bookingPIN";
                }

                if (booking.getLapsedBooking() == true) {
                    model.addAttribute("error", "Invalid Booking. Booking is lapsed");
                    return "bookingPIN";
                }

                model.addAttribute("booking", booking);
                String currentDateTime = booking.getStartTime().substring(0, 16);
                model.addAttribute("currentDateTime", currentDateTime);
                check = true;
                break;
            }
        }
        if (!check) {
            model.addAttribute("error", "Booking ID does not exist");
            return "bookingPIN";
        }

        model.addAttribute("testingSiteList", testingSiteList);

        return "modifyBooking";
    }

    @PostMapping("/verifyPINCode")
    public String postVerifyPinCode(@ModelAttribute("phoneCallForm") PhoneCallForm phoneCallForm, Model model) {

        List<Booking> bookingList = getBookingList();
        List<TestingSite> testingSiteList = getTestingSiteList();

        boolean check = false;

        for (Booking booking : bookingList) {
            if (booking.getSmsPin().equals(phoneCallForm.getPinCode())) {

                if (booking.getTestingDone() == true) {
                    model.addAttribute("error2", "Invalid Booking. Covid Test has performed");
                    return "bookingPIN";
                }

                if (booking.getCancelBooking() == true) {
                    model.addAttribute("error2", "Invalid Booking. Booking is cancelled");
                    return "bookingPIN";
                }

                if (booking.getLapsedBooking() == true) {
                    model.addAttribute("error2", "Invalid Booking. Booking is lapsed");
                    return "bookingPIN";
                }

                model.addAttribute("booking", booking);
                String currentDateTime = booking.getStartTime().substring(0, 16);
                model.addAttribute("currentDateTime", currentDateTime);
                check = true;
                break;
            }
        }

        if (!check) {
            model.addAttribute("error2", "PIN Code does not exist");
            return "bookingPIN";
        }

        model.addAttribute("testingSiteList", testingSiteList);

        return "modifyBooking";
    }

}
