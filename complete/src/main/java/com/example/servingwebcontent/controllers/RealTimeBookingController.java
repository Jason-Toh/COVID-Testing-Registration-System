package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.APIfactory;
import com.example.servingwebcontent.api.Get;
import com.example.servingwebcontent.api.TestingSiteFactory;
import com.example.servingwebcontent.models.AuthenticateSingleton;
import com.example.servingwebcontent.models.Booking;
import com.example.servingwebcontent.models.TestingSite;
import com.example.servingwebcontent.models.User;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class RealTimeBookingController {
    AuthenticateSingleton authenticateInstance = AuthenticateSingleton.getInstance();

    public List<Booking> getBookingListUsingTestingSite() throws InterruptedException, ParseException, IOException, java.text.ParseException {
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

    public List<Booking> checkDifferenceTime(List<Booking> bookings) throws java.text.ParseException {
        // check the time difference of time and local time
        // see whether it is smaller than the set range in second
        int timeRangeAllowed = 3600;   //In 1 hrs
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currTime = LocalDateTime.now().toString();
        Date currentTime = formatter.parse(currTime);
        List<Booking> bookings1 = new ArrayList<>();
        for (Booking booking : bookings){
            System.out.println(booking.getRecentUpdateTime());
            if(!booking.getRecentUpdateTime().equals("")){
                Date recentTime = formatter.parse(booking.getRecentUpdateTime());

                System.out.println((currentTime.getTime()-recentTime.getTime())/1000);

                if((currentTime.getTime()-recentTime.getTime())/1000 < timeRangeAllowed){
                    bookings1.add(booking);
                }
            }
        }
        return bookings1;
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

    @GetMapping("/realTimeBookingInfoPage")
    public String getRealTimeInfoPage(Model model) throws InterruptedException, ParseException, IOException, java.text.ParseException {
        if (!authenticateInstance.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        if (!authenticateInstance.getUser().isReceptionist()) {
            return "notAuthorised";
        }

        List<Booking> bookings = checkDifferenceTime(getBookingListUsingTestingSite());



        model.addAttribute("bookings", bookings);

        return "realTimeBookingInfoPage";
    }

}