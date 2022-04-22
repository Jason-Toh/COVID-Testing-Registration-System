package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.*;
import com.example.servingwebcontent.apiclasses.TestType;
import com.example.servingwebcontent.apiclasses.User;
import com.example.servingwebcontent.domain.BookingForm;
import com.example.servingwebcontent.domain.InterviewForm;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class InterviewController {

    private String api = "NrMhfCkHTjJjzHTWR8z8nP6FjcGg8K";

    @GetMapping("/interview")
    public String getRegister(Model model) {
        // 1. Interview Form
        InterviewForm interviewForm = new InterviewForm();
        model.addAttribute("interviewForm", interviewForm);


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
            throws IOException, InterruptedException {
        System.out.println(interviewForm);

        return "interview";
    }

}
