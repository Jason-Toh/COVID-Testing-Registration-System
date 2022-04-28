package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.*;
import com.example.servingwebcontent.apiclasses.Booking;
import com.example.servingwebcontent.apiclasses.TestType;
import com.example.servingwebcontent.apiclasses.TestingSite;
import com.example.servingwebcontent.apiclasses.User;
import com.example.servingwebcontent.domain.BookingForm;
import com.example.servingwebcontent.domain.InterviewForm;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class InterviewController {

    @GetMapping("/interview")
    public String getRegister(Model model) {

        // 1. Interview Form
        InterviewForm interviewForm = new InterviewForm();
        model.addAttribute("interviewForm", interviewForm);
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
        return "interview";
    }

    @PostMapping("/interview")
    public String submitInterviewForm(@ModelAttribute("interviewForm") InterviewForm interviewForm)
            throws IOException, InterruptedException, ParseException {

        APIfactory factory2 = new BookingFactory(System.getenv("API_KEY"));
        Get bookingGet = factory2.createGet();
        Collection jsonGet = bookingGet.getApi();
        Iterator<Booking> iterator = jsonGet.iterator();

        String bookingId = null;
        System.out.println(jsonGet);

        while (iterator.hasNext()) {

            if (iterator.next().getSmsPin().equals(interviewForm.getPinCode())) {
                bookingId = iterator.next().getBookingId();
            }
        }
        String patientStatus = "Headache: " + interviewForm.getHeadache() +
                "loss taste and smell: " + interviewForm.getLossTasteAndSmell() +
                "sore throat: " + interviewForm.getSoreThroat() +
                "muscle pain: " + interviewForm.getMusclePain() +
                "shaking: " + interviewForm.getShaking() +
                "close contact: " + interviewForm.getCloseContact();

        APIfactory factory3 = new CovidTestFactory(System.getenv("API_KEY"), interviewForm.getTestType(),
                interviewForm.getPatient(), interviewForm.getAdministrator(), bookingId, patientStatus);
        Post covidTestPost = factory3.createPost();
        String jsonPost = covidTestPost.postApi();

        return "interview";
    }

}
