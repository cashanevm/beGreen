package ua.knu.be_green.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static ua.knu.be_green.service.constants.ApplicationConstants.MVC_APPLICATION_PREFIX;
import static ua.knu.be_green.service.constants.ApplicationConstants.REST_APPLICATION_PREFIX;

@Controller
public class TestController {
    private static final List<String> ENDPOINTS = new ArrayList<>();

    static {
        addRestEndpoint("/map/marker/all");
        addRestEndpoint("/map/marker/7daa7a99-96ec-433c-88b2-6c0b1d744da6");

        addMvcEndpoint("/map/marker/choice");
    }

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("endpoints", ENDPOINTS);
        return "test";
    }

    private static void addRestEndpoint(String name) {
        ENDPOINTS.add(REST_APPLICATION_PREFIX + name);
    }
    private static void addMvcEndpoint(String name) {
        ENDPOINTS.add(MVC_APPLICATION_PREFIX + name);
    }
}
