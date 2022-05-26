package ua.knu.beGreen.web.controller.mvc;

import ua.knu.beGreen.service.model.UserModel;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Parameter;

import static ua.knu.beGreen.service.constants.ApplicationConstants.MVC_APPLICATION_PREFIX;

@Controller
@RequestMapping(MVC_APPLICATION_PREFIX)
public class HomeController {
    @GetMapping("/home")
    public String greeting(Model model, @Parameter(hidden = true) @AuthenticationPrincipal UserModel userModel) {
        model.addAttribute("username", userModel.getUsername());
        model.addAttribute("userCurrency", userModel.getCurrency() == null ? 0 : userModel.getCurrency());
        return "home";
    }
}
