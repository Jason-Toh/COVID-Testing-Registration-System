package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.*;
import com.example.servingwebcontent.enumeration.TestType;
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
        APIfactory factory = new TestingSiteFactory(api);
        Get testingSiteGet = factory.createGet();
        Collection<TestingSite>testingSites = testingSiteGet.getApi();

        // TO DO SEARCH FOR TESTING SITE WHICH THAT ADMIN RESPONSIBLE
        User user = authenticateInstance.getUser();
        for(TestingSite testingSite: testingSites){
            if(testingSite.getId().equals(user.getTestingSiteId())){
                return testingSite.getBookings();
            }
        }
        return null;
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

        return "adminPanel";
    }
    @RequestMapping("/adminPanel/{id}")
    public String showTestingSite(@PathVariable String id, Model model)
            throws IOException, InterruptedException, ParseException {

        APIfactory apiFactory = new BookingFactory(System.getenv("API_KEY"));
        Delete deleteBooking = apiFactory.createDelete();
        deleteBooking.deleteApi(id);

        List<Booking> bookings = getBookingListUsingTestingSite();
        model.addAttribute("bookings", bookings);

        return "adminPanel";
    }

}
