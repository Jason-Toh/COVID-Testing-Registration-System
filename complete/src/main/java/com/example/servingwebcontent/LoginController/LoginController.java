package com.example.servingwebcontent.LoginController;

import com.example.servingwebcontent.domain.UserLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("UserLogin", new UserLogin());
        return "login";
    }

    @PostMapping("/testing_site")
    public String postlogin(@ModelAttribute UserLogin userLogin, BindingResult result, Model model) {
        model.addAttribute("UserLogin", userLogin);
        return "testing_site";
    }
}
