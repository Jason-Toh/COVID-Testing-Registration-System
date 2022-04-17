package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.domain.UserLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

    // Check for Credentials
    @PostMapping("/login")
    public String postlogin(@ModelAttribute(name = "userlogin") UserLogin login, Model m)
            throws IOException, InterruptedException {
        String uname = login.getUserName();
        String pass = login.getPassword();
        if (checkLoginFromAPI(uname, pass)) {
            m.addAttribute("userName", uname);
            m.addAttribute("password", pass);
            return "testing-site";
        }
        m.addAttribute("error", "Incorrect Username & Password");
        return "login";

    }

//    @PostMapping("/testing-site")
//    public String postTestingSite(@ModelAttribute UserLogin userLogin, BindingResult result, Model model) {
//        model.addAttribute("UserLogin", userLogin);
//        return "testing-site";
//    }

    public boolean checkLoginFromAPI(String userName, String password) throws IOException, InterruptedException {
        boolean flag = false;
        String myApiKey = "NrMhfCkHTjJjzHTWR8z8nP6FjcGg8K";
        String rootUrl = "https://fit3077.com/api/v1";
        String usersUrl = rootUrl + "/user";
        String usersLoginUrl = usersUrl + "/login";

        String jsonString = "{" +
                "\"userName\":\"" + userName + "\"," +
                "\"password\":\"" + password + "\"" +
                "}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(usersLoginUrl + "?jwt=true")) // Return a JWT so we can
                                                                                              // use it in Part 5 later.
                .setHeader("Authorization", myApiKey)
                .header("Content-Type", "application/json") // This header needs to be set when sending a JSON request
                                                            // body.
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            flag = true;
        }
        return flag;
    }
}