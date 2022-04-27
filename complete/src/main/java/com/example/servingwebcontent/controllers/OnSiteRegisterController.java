package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.*;
import com.example.servingwebcontent.apiclasses.TestType;
import com.example.servingwebcontent.apiclasses.TestingSite;
import com.example.servingwebcontent.apiclasses.User;
import com.example.servingwebcontent.domain.BookingForm;

import com.example.servingwebcontent.tool.MyQr;
import com.example.servingwebcontent.tool.RandomPinGenerator;
import com.example.servingwebcontent.tool.RandomStringGenerator;
import com.google.zxing.WriterException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
public class OnSiteRegisterController {

    @GetMapping("/register")
    public String getRegister(Model model) {
        // 1.
        BookingForm bookingForm = new BookingForm();
        model.addAttribute("bookingForm", bookingForm);

        // 2.1 Get testing-sites and put it into model
        // API factory
        APIfactory factory = new TestingSiteFactory(System.getenv("API"));
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
        APIfactory factory1 = new UserFactory(System.getenv("API"));
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

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("bookingForm") BookingForm bookingForm)
            throws IOException, InterruptedException, WriterException {
        // Make booking post here
        APIfactory factory3 = new BookingFactory(
                System.getenv("API"), bookingForm.getCustomerUsername(), bookingForm.getTestingSite(),
                bookingForm.getTime());
        Post bookingPost = factory3.createPost();
        String jsonPost = bookingPost.postApi();

        // Convert booking return JSON string to JSONObject and get the booking id
        JSONObject book = new JSONObject(jsonPost);
        String bookingId = book.get("id") + "";

        // Post Qr code String
        if (bookingForm.isOnHomeBooking()) {
            // Generate random String

            APIfactory factory4 = new PhotoFactory(System.getenv("API"), bookingForm.getQr());
            Post photoPost = factory4.createPost();
            String jsonPost1 = photoPost.postApi();

        }

        return "register";
    }

}
