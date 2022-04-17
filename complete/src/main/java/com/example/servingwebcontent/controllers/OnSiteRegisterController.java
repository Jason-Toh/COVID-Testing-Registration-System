package com.example.servingwebcontent.controllers;

import com.example.servingwebcontent.api.APIfactory;
import com.example.servingwebcontent.api.Get;
import com.example.servingwebcontent.api.TestingSiteFactory;
import com.example.servingwebcontent.apiclasses.TestType;
import com.example.servingwebcontent.apiclasses.TestingSite;
import com.example.servingwebcontent.domain.UserLogin;
import com.example.servingwebcontent.model.SMSPinModel;
import com.example.servingwebcontent.model.Test;
import com.example.servingwebcontent.model.TestTypeModel;
import com.example.servingwebcontent.model.TestingSiteModel;
import com.example.servingwebcontent.tool.RandomPinGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
public class OnSiteRegisterController {
    private String api = "NrMhfCkHTjJjzHTWR8z8nP6FjcGg8K";

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("UserLogin", new UserLogin());

        // Demo purpose
        List<Test> tests = new ArrayList<>();
        tests.add(new Test("heyman its mario", "why r u here", 3400));
        tests.add(new Test("heyman its mom", "why r u here", 5700));
        model.addAttribute("tests", tests);

        // Get testing-sites and put it into model
        // API factory
        APIfactory factory = new TestingSiteFactory(api);
        Get testingSiteGet = factory.createGet();

        List<TestingSiteModel> testingSiteModels = new ArrayList<>();

        try {
            // Testing-site collection
            Collection<TestingSite> testingSites = testingSiteGet.getApi();
            Iterator<TestingSite> iterator = testingSites.iterator();
            while (iterator.hasNext()) {
                testingSiteModels.add(new TestingSiteModel(iterator.next().getName()));
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        model.addAttribute("testingSiteModels", testingSiteModels);

        // Get test-type and put it into model
        List<TestTypeModel> testTypeModels = new ArrayList<>();
        for (TestType testType : TestType.values()) {
            testTypeModels.add(new TestTypeModel(testType+""));
        }
        model.addAttribute("testTypeModels", testTypeModels);

        // Get smsPin
        RandomPinGenerator rad = new RandomPinGenerator();
        String smsPin = rad.getPin();
        SMSPinModel smsPinModel = new SMSPinModel(smsPin);
//        model.addAttribute("smsPinModel", smsPinModel);

        return "register";
    }
    @PostMapping("/register")
    public String postTestingSite(@ModelAttribute(name = "userlogin") UserLogin login, Model model) {
        System.out.println("mom");
        return "register";
    }
}
