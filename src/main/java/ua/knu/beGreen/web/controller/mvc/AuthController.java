package ua.knu.beGreen.web.controller.mvc;

import ua.knu.beGreen.persistence.entity.Role;
import ua.knu.beGreen.service.api.MailingService;
import ua.knu.beGreen.service.model.UserModel;
import ua.knu.beGreen.service.impl.UserServiceImpl;
import ua.knu.beGreen.web.dto.request.UserRequestDto;
import ua.knu.beGreen.web.mapper.WebLayerMapper;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

import static ua.knu.beGreen.service.constants.ApplicationConstants.MVC_APPLICATION_PREFIX;


@Controller
@RequestMapping(MVC_APPLICATION_PREFIX + "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceImpl userService;
    private final MailingService mailingService;

    @GetMapping("/registration")
    public String registration() {
        return "auth/registration";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam(name = "password2") String password2,
            @Valid UserRequestDto userRequestDto,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {
        UserModel userModel = WebLayerMapper.I.toUserModel(userRequestDto);
        userModel.setRoles(Collections.singleton(Role.valueOf(userRequestDto.getRole())));


        if (userModel.getPassword() != null && !userModel.getPassword().equals(password2)) {
            model.addAttribute("passwordCompError", "Passwords are different!");
            model.addAttribute("user", userModel);
            return "auth/registration";
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrorsMap(bindingResult);

            model.mergeAttributes(errors);
            model.addAttribute("user", userModel);
            return "auth/registration";
        }

        if (!userService.addUser(userModel)) {
            model.addAttribute("usernameError", "User exists!");
            return "auth/registration";
        }

        mailingService.sendMessage(userModel, request.getRequestURL().toString().replace(request.getRequestURI(), ""));

        model.addAttribute("message", "Сonfirm your email: " + userModel.getEmail() + " please!");

        return "auth/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        return "auth/login";
    }
}
