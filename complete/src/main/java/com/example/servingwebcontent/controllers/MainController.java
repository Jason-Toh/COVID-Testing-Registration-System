package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.models.Authenticate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {

        if (!Authenticate.getIsUserAuthenticated()) {
            return "redirect:/login";
        }

        return "home-page";
    }
}
