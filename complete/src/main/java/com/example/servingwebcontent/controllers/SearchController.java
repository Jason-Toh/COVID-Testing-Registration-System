package com.example.servingwebcontent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class SearchController {

    public String displayTestingSites(Model model) {
        return "testing-sites";
    }
}
