package com.example.servingwebcontent.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BrowseTestingSiteController {

    @GetMapping("/browse")
    public String browseTestingSite() {
        return "browseTestingSite";
    }

    @GetMapping("/testing-sites")
    public String displayTestingSites(Model model) throws IOException, InterruptedException {
        String myApiKey = "qBDMKwFLh6bRJTnJqnfRGNDM6KRphz";
        String rootUrl = "https://fit3077.com/api/v1";
        String testingSitesUrl = rootUrl + "/testing-site";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(testingSitesUrl))
                .setHeader("Authorization", myApiKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNodes);

        model.addAttribute("testingSites", jsonNodes);

        return "testing-sites";
    }
}
