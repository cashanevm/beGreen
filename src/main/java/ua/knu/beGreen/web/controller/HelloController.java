package ua.knu.beGreen.web.controller;

import ua.knu.beGreen.service.model.UserModel;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Parameter;

@Controller
public class HelloController {
    @GetMapping("/")
    public String greeting(Model model, @Parameter(hidden = true) @AuthenticationPrincipal UserModel userModel) {
        System.out.println(userModel);
      ;
        model.addAttribute("username", userModel.getUsername());
        model.addAttribute("roles", userModel.getRoles().toString());
        model.addAttribute("ui", "/app/map/marker/choice");
        model.addAttribute("back", "/swagger-ui/index.html?configUrl=/be-green/v3/api-docs/swagger-config#/");
        return "hello";
    }
}
