package com.example.servingwebcontent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InterviewController {
    @GetMapping("/interview")
    public String getRegister(Model model) {
        return "interview";
    }

}
