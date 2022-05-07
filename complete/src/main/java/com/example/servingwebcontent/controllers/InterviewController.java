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

    public List<User> getAdministererList() {
        // 2.2
        APIfactory<User> userFactory = new UserFactory(System.getenv("API_KEY"));
        Get<User> userGet = userFactory.createGet();

        List<User> administererList = new ArrayList<>();

        try {
            // User collection
            Collection<User> userCollection = userGet.getApi();

            for (User user : userCollection) {
                if (user.isHealthcareWorker()) {
                    administererList.add(user);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return administererList;
    }

    public List<User> getUserList() {
        // 2.2
        APIfactory<User> userFactory = new UserFactory(System.getenv("API_KEY"));
        Get<User> userGet = userFactory.createGet();

        List<User> userList = new ArrayList<>();

        try {
            // User collection
            Collection<User> userCollection = userGet.getApi();

            for (User user : userCollection) {
                if (user.isCustomer()) {
                    userList.add(user);
                }
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

        // // 1. Interview Form
        // InterviewForm interviewForm = new InterviewForm();
        // model.addAttribute("interviewForm", interviewForm);

        // List<User> userList = getUserList();
        // model.addAttribute("userList", userList);

        // List<User> administererList = getAdministererList();
        // model.addAttribute("administererList", administererList);

        // // 3. Get test-type and put it into model
        // List<String> testTypeList = getTestTypeList();
        // model.addAttribute("testTypeList", testTypeList);

        // return "interview";
        return "pinInterview";
    }

    @PostMapping("verifyPinCode")
    public String verifyPinCode(@ModelAttribute("interviewForm") InterviewForm interviewForm, Model model)
            throws IOException, InterruptedException, ParseException {

        APIfactory<Booking> bookingFactory = new BookingFactory(System.getenv("API_KEY"));
        Get<Booking> bookingGet = bookingFactory.createGet();

        String pinCode = interviewForm.getPinCode();
        boolean check = false;
        boolean check2 = false;

        String patientName = "";
        String patientId = "";

        Collection<Booking> bookingCollection = bookingGet.getApi();

        for (Booking booking : bookingCollection) {
            if (booking.getSmsPin().equals(pinCode)) {
                check = true;
                patientId = booking.getCustomerId();
                patientName = booking.getCustomerName();
                if (!booking.getStatus().equals("COMPLETED")) {
                    check2 = true;
                }
                break;
            }
        }

        if (!check) {
            model.addAttribute("error", "Pin Code does not exist. Please try again");

            return "pinInterview";
        }

        if (check2) {
            model.addAttribute("error", "Booking Status is not COMPLETED");

            return "pinInterview";
        }

        User administerer = authenticateInstance.getUser();

        // 1. Interview Form
        InterviewForm interviewForm2 = new InterviewForm();
        model.addAttribute("interviewForm", interviewForm2);

        model.addAttribute("pinCode", pinCode);

        model.addAttribute("patientId", patientId);
        model.addAttribute("patientName", patientName);

        model.addAttribute("administerer", administerer);

        // List<User> userList = getUserList();
        // model.addAttribute("userList", userList);

        // List<User> administererList = getAdministererList();
        // model.addAttribute("administererList", administererList);

        // 3. Get test-type and put it into model
        List<String> testTypeList = getTestTypeList();
        model.addAttribute("testTypeList", testTypeList);

        return "interview";
    }

    @PostMapping("/interview")
    public String submitInterviewForm(@ModelAttribute("interviewForm") InterviewForm interviewForm, Model model)
            throws IOException, InterruptedException, ParseException {

        // TODO Fix Interview Submit Form
        // FIXME interview.html needs fixing as well

        APIfactory<Booking> bookingFactory = new BookingFactory(System.getenv("API_KEY"));
        Get<Booking> bookingGet = bookingFactory.createGet();
        Collection<Booking> bookingCollection = bookingGet.getApi();

        String pinCode = interviewForm.getPinCode();

        String bookingId = "";

        for (Booking booking : bookingCollection) {
            if (booking.getSmsPin().equals(pinCode)) {
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
                interviewForm.getPatient(), interviewForm.getAdministerer(), bookingId, patientStatus);
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
