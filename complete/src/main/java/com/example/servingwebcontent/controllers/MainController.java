package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.models.Authenticate;
import com.example.servingwebcontent.models.User;

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

        User user = Authenticate.getUser();

        String userName = user.getUserName();
        model.addAttribute("userName", userName);

        if (user.isCustomer()) {
            model.addAttribute("role", "Customer");
        } else if (user.isHealthcareWorker()) {
            model.addAttribute("role", "Administrator");
        } else if (user.isReceptionist()) {
            model.addAttribute("role", "Health Care Worker");
        } else {
            model.addAttribute("role", "No Role");
        }

        return "homePage";
    }
}
