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
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("UserLogin", new UserLogin());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("UserLogin", new UserLogin());
        return "login";
    }

    //Check for Credentials
    @PostMapping("/login")
    public String postlogin(@ModelAttribute(name="userlogin") UserLogin login, Model m) {
        String uname = login.getUserName();
        String pass = login.getPassword();
        if(uname.equals("Admin") && pass.equals("Admin")) {
            m.addAttribute("uname", uname);
            m.addAttribute("pass", pass);
            return "testing-site";
        }
        m.addAttribute("error", "Incorrect Username & Password");
        return "/login";

    }


    @PostMapping("/testing-site")
    public String postlogin(@ModelAttribute UserLogin userLogin, BindingResult result, Model model) {
        model.addAttribute("UserLogin", userLogin);
        return "testing-site";
    }
}
