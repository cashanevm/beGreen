package ua.knu.beGreen.web.controller.rest;

import ua.knu.beGreen.service.api.QRCodeService;
import ua.knu.beGreen.service.api.impl.QRCodeServiceImpl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static ua.knu.beGreen.service.constants.ApplicationConstants.REST_APPLICATION_PREFIX;

@RestController
@RequestMapping(REST_APPLICATION_PREFIX + "/quick/code")
@RequiredArgsConstructor
public class QRCodeController {
    private final QRCodeService codeService;

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType() {
        return codeService.toByteArray(
                codeService.generateEAN13BarcodeImage("https://springnevm.herokuapp.com/be-green/"),
                "jpg"
        );
    }

    @GetMapping(value = "/add/kg/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageToAddKg(@PathVariable String id, HttpServletRequest request) {
        return codeService.toByteArray(
                codeService.generateEAN13BarcodeImage(request.getRequestURL().toString().replace(request.getRequestURI(), "") + "/be-green/app/pointing/add/kg/container/"+ id),
                "jpg"
        );
    }
}
