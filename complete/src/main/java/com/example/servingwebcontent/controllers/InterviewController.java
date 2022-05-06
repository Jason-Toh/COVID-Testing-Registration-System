package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.*;
import com.example.servingwebcontent.enumeration.BookingStatus;
import com.example.servingwebcontent.enumeration.TestType;
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

    public List<User> getUserList() {
        // 2.2
        APIfactory<User> userFactory = new UserFactory(System.getenv("API_KEY"));
        Get<User> userGet = userFactory.createGet();

        List<User> userList = new ArrayList<>();

        try {
            // User collection
            Collection<User> userCollection = userGet.getApi();

            for (User user : userCollection) {
                userList.add(user);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return userList;
    }

    public List<String> getTestTypeList() {

        List<String> testTypeList = new ArrayList<>();
        for (TestType testType : TestType.values()) {
            testTypeList.add(testType + "");
        }

        return testTypeList;
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

        List<User> userList = getUserList();
        model.addAttribute("userList", userList);

        // 3. Get test-type and put it into model
        List<String> testTypeList = getTestTypeList();
        model.addAttribute("testTypeList", testTypeList);

        return "interview";
    }

    @PostMapping("/interview")
    public String submitInterviewForm(@ModelAttribute("interviewForm") InterviewForm interviewForm, Model model)
            throws IOException, InterruptedException, ParseException {

        APIfactory<Booking> bookingFactory = new BookingFactory(System.getenv("API_KEY"));
        Get<Booking> bookingGet = bookingFactory.createGet();
        Collection<Booking> bookingCollection = bookingGet.getApi();

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

            List<User> userList = getUserList();
            model.addAttribute("userList", userList);

            // 3. Get test-type and put it into model
            List<String> testTypeList = getTestTypeList();
            model.addAttribute("testTypeList", testTypeList);

            model.addAttribute("error", "Pin Code does not exist. Please try again");

            return "interview";
        }

        String bookingId = null;
        for (Booking booking : bookingCollection) {
            if (booking.getSmsPin().equals(interviewForm.getPinCode())) {
                bookingId = booking.getBookingId();
                break;
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

        APIfactory<CovidTest> covidTestFactory = new CovidTestFactory(System.getenv("API_KEY"),
                interviewForm.getTestType(),
                interviewForm.getPatient(), interviewForm.getAdministrator(), bookingId, patientStatus);
        Post covidTestPost = covidTestFactory.createPost();
        // String jsonPost = covidTestPost.postApi();
        covidTestPost.postApi();

        // PATCH the symptom into the additional info of the booking api using it
        // booking id
        // change the booking status to completed

        APIfactory<Booking> bookingFactory2 = new BookingFactory(System.getenv("API_KEY"), bookingId, patientStatus,
                BookingStatus.COMPLETED);
        Patch bookingPatch = bookingFactory2.createPatch();
        // String returnValue = bookingPatch.patchApi();
        bookingPatch.patchApi();

        return "testingDone";
    }

}
