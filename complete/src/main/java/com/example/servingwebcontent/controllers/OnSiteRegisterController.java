package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.*;
import com.example.servingwebcontent.apiclasses.TestType;
import com.example.servingwebcontent.apiclasses.TestingSite;
import com.example.servingwebcontent.apiclasses.User;
import com.example.servingwebcontent.domain.BookingForm;

import com.example.servingwebcontent.tool.RandomPinGenerator;
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
    private String api = "NrMhfCkHTjJjzHTWR8z8nP6FjcGg8K";

    @GetMapping("/register")
    public String getRegister(Model model) {
        //1.
        BookingForm bookingForm = new BookingForm();
        model.addAttribute("bookingForm", bookingForm);


        //2.1 Get testing-sites and put it into model
        // API factory
        APIfactory factory = new TestingSiteFactory(api);
        Get testingSiteGet = factory.createGet();

        List<TestingSite> testingSiteModels = new ArrayList<>();

        try {
            // Testing-site collection
            Collection<TestingSite> testingSites = testingSiteGet.getApi();
            Iterator<TestingSite> iterator = testingSites.iterator();
            while (iterator.hasNext()) {
                testingSiteModels.add(iterator.next());
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        model.addAttribute("testingSiteModels", testingSiteModels);
        //2.2
        APIfactory factory1 = new UserFactory(api);
        Get userGet = factory1.createGet();

        List<User> userModels = new ArrayList<>();

        try {
            // Testing-site collection
            Collection<User> users = userGet.getApi();
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
                userModels.add(iterator.next());
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }

        model.addAttribute("userModels", userModels);



        //3. Get test-type and put it into model
        List<String> testTypeModels = new ArrayList<>();
        for (TestType testType : TestType.values()) {
            testTypeModels.add(testType+"");
        }
        model.addAttribute("testTypeModels", testTypeModels);

        //4. Get smsPin
        RandomPinGenerator rad = new RandomPinGenerator();
        String smsPin = rad.getPin();
        // model.addAttribute("smsPinModel", smsPinModel);

        return "register";
    }
    @PostMapping("/register")
    public String submitForm(@ModelAttribute("bookingForm") BookingForm bookingForm) throws IOException, InterruptedException {
        System.out.println(bookingForm);
        // Make booking post here
        APIfactory factory3 = new BookingFactory(api,bookingForm.getCustomerUsername(),bookingForm.getTestingSite(),bookingForm.getTime());
        Post bookingPost = factory3.createPost();
        bookingPost.postApi();
        // Make covid-test post here

        return "register";
    }
}
