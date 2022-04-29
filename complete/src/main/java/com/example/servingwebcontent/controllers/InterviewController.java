package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.*;
import com.example.servingwebcontent.models.*;
import com.example.servingwebcontent.domain.InterviewForm;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class InterviewController {

    AuthenticateSingleton authenticateInstance = AuthenticateSingleton.getInstance();

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

    public List<String> getTestTypeModels() {

        List<String> testTypeModels = new ArrayList<>();
        for (TestType testType : TestType.values()) {
            testTypeModels.add(testType + "");
        }

        return testTypeModels;
    }

    @GetMapping("/interview")
    public String getRegister(Model model) {

        if (!authenticateInstance.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        if (!authenticateInstance.getUser().isHealthcareWorker()) {
            return "notAuthorised";
        }

        // 1. Interview Form
        InterviewForm interviewForm = new InterviewForm();
        model.addAttribute("interviewForm", interviewForm);

        List<User> userModels = getUsers();
        model.addAttribute("userModels", userModels);

        // 3. Get test-type and put it into model
        List<String> testTypeModels = getTestTypeModels();
        model.addAttribute("testTypeModels", testTypeModels);

        return "interview";
    }

    @PostMapping("/interview")
    public String submitInterviewForm(@ModelAttribute("interviewForm") InterviewForm interviewForm, Model model)
            throws IOException, InterruptedException, ParseException {

        APIfactory factory2 = new BookingFactory(System.getenv("API_KEY"));
        Get bookingGet = factory2.createGet();
        Collection<Booking> bookingCollection = bookingGet.getApi();
        Iterator<Booking> iterator = bookingCollection.iterator();

        String pinCode = interviewForm.getPinCode();

        boolean check = false;
        for (Booking booking : bookingCollection) {
            if (booking.getSmsPin().equals(pinCode)) {
                check = true;
                break;
            }
        }

        if (!check) {
            // 1. Interview Form
            InterviewForm interviewForm2 = new InterviewForm();
            model.addAttribute("interviewForm", interviewForm2);

            List<User> userModels = getUsers();
            model.addAttribute("userModels", userModels);

            // 3. Get test-type and put it into model
            List<String> testTypeModels = getTestTypeModels();
            model.addAttribute("testTypeModels", testTypeModels);

            model.addAttribute("error", "Pin Code does not exist. Please try again");

            return "interview";
        }

        String bookingId = null;

        while (iterator.hasNext()) {
            if (iterator.next().getSmsPin().equals(interviewForm.getPinCode())) {
                bookingId = iterator.next().getBookingId();
            }
        }

        String patientStatus = "no symptoms";
        if (interviewForm.getHeadache() || interviewForm.getLossTasteAndSmell() || interviewForm.getSoreThroat()
                || interviewForm.getMusclePain() || interviewForm.getShaking() || interviewForm.getCloseContact()) {
            patientStatus = "Headache: " + interviewForm.getHeadache() +
                    ", loss taste and smell: " + interviewForm.getLossTasteAndSmell() +
                    ", sore throat: " + interviewForm.getSoreThroat() +
                    ", muscle pain: " + interviewForm.getMusclePain() +
                    ", shaking: " + interviewForm.getShaking() +
                    ", close contact: " + interviewForm.getCloseContact();
        }

        APIfactory factory3 = new CovidTestFactory(System.getenv("API_KEY"), interviewForm.getTestType(),
                interviewForm.getPatient(), interviewForm.getAdministrator(), bookingId, patientStatus);
        Post covidTestPost = factory3.createPost();
        String jsonPost = covidTestPost.postApi();

        // PATCH the symptom into the additional info of the booking api using it
        // booking id
        // change the booking status to completed

        APIfactory bookingFactory = new BookingFactory(System.getenv("API_KEY"), bookingId, patientStatus,
                BookingStatus.COMPLETED);
        Patch bookingPatch = bookingFactory.createPatch();
        String returnValue = bookingPatch.patchApi();
        // System.out.println(returnValue);

        return "testingDone";
    }

}
