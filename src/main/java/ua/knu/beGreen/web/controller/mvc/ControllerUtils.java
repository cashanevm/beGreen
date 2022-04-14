package ua.knu.beGreen.web.controller.mvc;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ControllerUtils {

    public static Map<String, String> getErrorsMap(BindingResult bindingResult) {
        Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        ));
        return errorsMap;
    }
}
