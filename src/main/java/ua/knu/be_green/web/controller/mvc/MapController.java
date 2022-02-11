package ua.knu.be_green.web.controller.mvc;

import ua.knu.be_green.web.controller.rest.MarkerController;
import ua.knu.be_green.web.dto.request.MarkerRequestDto;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

import static ua.knu.be_green.service.constants.ApplicationConstants.MVC_APPLICATION_PREFIX;

@Controller
@RequestMapping(MVC_APPLICATION_PREFIX + "/map")
@RequiredArgsConstructor
public class MapController {
    private final MarkerController markerController;

    /**
     * Choice marker.
     *
     *
     * @return add-marker.html
     */
    @GetMapping("/marker/choice")
    public String choiceMarker() {
        return "map/choose-marker";
    }

    /**
     * Show add new marker form.
     *
     *
     * @return add-new-marker.html
     */
    @GetMapping("/marker/new")
    public String showAddNewMarkerForm(Model model) {
        model.addAttribute("marker_request_dto", new MarkerRequestDto());
        return "map/add-new-marker";
    }

    /**
     * Choice marker.
     *
     *
     * @return ...
     */
    @PostMapping("/marker/new")
    public String addNewMarker(@ModelAttribute("marker_request_dto") @Valid MarkerRequestDto requestDto,
                               BindingResult bindingResult,
                               @RequestParam(name ="lat")  String lat,
                               @RequestParam(name ="lng")  String lng,
                               Model model) {
        requestDto.setLat(lat);
        requestDto.setLng(lng);

        model.addAttribute("marker_request_dto", requestDto);

        if (bindingResult.hasErrors()) {
            return "map/add-new-marker";
        }

        markerController.saveMarker(requestDto);

        return "redirect:" + MVC_APPLICATION_PREFIX + "/map/marker/choice";
    }
}
