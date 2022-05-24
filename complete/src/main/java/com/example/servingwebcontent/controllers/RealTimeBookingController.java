package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.models.AuthenticateSingleton;
import com.example.servingwebcontent.models.Booking;
import com.example.servingwebcontent.observer.BookingData;
import com.example.servingwebcontent.observer.CurrentBookingDisplay;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class RealTimeBookingController {
    AuthenticateSingleton authenticateInstance = AuthenticateSingleton.getInstance();

    @GetMapping("/realTimeBookingInfoPage")
    public String getRealTimeInfoPage(Model model)
            throws InterruptedException, ParseException, IOException, java.text.ParseException {
        if (!authenticateInstance.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        if (!authenticateInstance.getUser().isReceptionist()) {
            return "notAuthorised";
        }
        // create objects for testing
        CurrentBookingDisplay currentBookingDisplay = new CurrentBookingDisplay();

        // pass the displays to Cricket data
        BookingData bookingData = new BookingData();

        // register display elements
        bookingData.registerObserver(currentBookingDisplay);

        List<Booking> bookings = bookingData.dataChanged();

        model.addAttribute("bookings", bookings);

        return "realTimeBookingInfoPage";
    }

}
