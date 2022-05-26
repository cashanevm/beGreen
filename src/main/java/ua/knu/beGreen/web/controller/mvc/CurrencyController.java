package ua.knu.beGreen.web.controller.mvc;

import ua.knu.beGreen.service.api.impl.UserServiceImpl;
import ua.knu.beGreen.service.model.UserModel;
import ua.knu.beGreen.web.dto.request.CurrencyRequestDto;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import static ua.knu.beGreen.service.constants.ApplicationConstants.MVC_APPLICATION_PREFIX;

@Controller
@RequestMapping(MVC_APPLICATION_PREFIX + "/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final UserServiceImpl userService;

    @GetMapping("/add")
    public String addCurrency(Model model, @Parameter(hidden = true) @AuthenticationPrincipal UserModel userModel) {
        CurrencyRequestDto requestDto = new CurrencyRequestDto();
        model.addAttribute("requestDto", requestDto);
        return "currency/add-currency-marker";
    }

    @PostMapping("/add")
    public String addCurrencyPost(@Valid CurrencyRequestDto requestDto, BindingResult bindingResult, Model model, @Parameter(hidden = true) @AuthenticationPrincipal UserModel userModel) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrorsMap(bindingResult);

            model.mergeAttributes(errors);
            model.addAttribute("requestDto", requestDto);
            return "currency/add-currency-marker";
        }

        Integer currency = userModel.getCurrency();

        userModel.setCurrency(currency == null ? requestDto.getCurrencyValue() : userModel.getCurrency() + requestDto.getCurrencyValue());

        userService.saveUser(userModel);

        return "redirect:/app/home";
    }
}
