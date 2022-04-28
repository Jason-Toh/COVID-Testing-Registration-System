package com.example.servingwebcontent.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.servingwebcontent.api.APIfactory;
import com.example.servingwebcontent.api.Get;
import com.example.servingwebcontent.api.TestingSiteFactory;
import com.example.servingwebcontent.models.TestingSite;
import com.example.servingwebcontent.domain.BrowseForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BrowseTestingSiteController {

    public List<TestingSite> getTestingSiteModels() {
        List<TestingSite> testingSiteModels = new ArrayList<>();

//        APIfactory factory = new TestingSiteFactory(API.getAPIKey());
        APIfactory factory = new TestingSiteFactory(System.getenv("API_KEY"));
        Get testingSiteGet = factory.createGet();

        try {
            // Testing-site collection
            Collection<TestingSite> testingSites = testingSiteGet.getApi();

            for (TestingSite testingSite : testingSites) {
                testingSiteModels.add(testingSite);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return testingSiteModels;
    }

    @GetMapping("/browse")
    public String browseTestingSite(Model model) {

        List<TestingSite> testingSiteModels = getTestingSiteModels();

        model.addAttribute("testingSites", testingSiteModels);

        return "testing-site/browse";
    }

    @PostMapping("/search")
    public String searchTestingSite(@ModelAttribute("browseForm") BrowseForm browseForm, Model model) {
//        APIfactory factory = new TestingSiteFactory(API.getAPIKey());
        APIfactory factory = new TestingSiteFactory(System.getenv("API_KEY"));
        Get testingSiteGet = factory.createGet();

        List<TestingSite> filteredTestingSiteModels = new ArrayList<>();

        try {
            // Testing-site collection
            Collection<TestingSite> testingSites = testingSiteGet.getApi();

            String suburbName = browseForm.getSuburbName().toLowerCase();

            // Search for a substring in a string
            for (TestingSite testingSite : testingSites) {
                // Filter the testing site based on suburb name
                if (testingSite.getName().toLowerCase().contains(suburbName)) {
                    filteredTestingSiteModels.add(testingSite);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        model.addAttribute("testingSites", filteredTestingSiteModels);

        return "testing-site/browse";
    }

    @PostMapping("/select")
    public String filterByTypeOfFacility(@ModelAttribute("browseForm") BrowseForm browseForm, Model model) {
//        APIfactory factory = new TestingSiteFactory(API.getAPIKey());
        APIfactory factory = new TestingSiteFactory(System.getenv("API_KEY"));

        Get testingSiteGet = factory.createGet();

        List<TestingSite> filteredTestingSiteModels = new ArrayList<>();

        try {
            // Testing-site collection
            Collection<TestingSite> testingSites = testingSiteGet.getApi();

            String typeOfFacility = browseForm.getTypeOfFacility().toLowerCase();

            // Search for a substring in a string
            for (TestingSite testingSite : testingSites) {
                // Filter the testing site based on type of facility
                if (testingSite.getAdditonalInfo().getTypeOfFacility().toLowerCase().contains(typeOfFacility)) {
                    filteredTestingSiteModels.add(testingSite);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        model.addAttribute("testingSites", filteredTestingSiteModels);

        return "testing-site/browse";
    }

    @RequestMapping("/show/{id}")
    public String showTestingSite(@PathVariable String id, Model model)
            throws IOException, InterruptedException {

        List<TestingSite> testingSiteModels = getTestingSiteModels();

        for (TestingSite testingSite : testingSiteModels) {
            if (id.equals(testingSite.getId())) {
                model.addAttribute("testingSite", testingSite);
                break;
            }
        }

        return "testing-site/show";
    }
}
