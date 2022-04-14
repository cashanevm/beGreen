package ua.knu.beGreen.web.controller.mvc;

import ua.knu.beGreen.service.api.ContainerService;
import ua.knu.beGreen.service.impl.UserServiceImpl;
import ua.knu.beGreen.service.model.ContainerModel;
import ua.knu.beGreen.service.model.MarkerModel;
import ua.knu.beGreen.service.model.UserModel;
import ua.knu.beGreen.web.controller.rest.MarkerController;
import ua.knu.beGreen.web.dto.request.ContainerRequestDto;
import ua.knu.beGreen.web.dto.request.MarkerRequestDto;
import ua.knu.beGreen.web.mapper.WebLayerMapper;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import static ua.knu.beGreen.service.constants.ApplicationConstants.MVC_APPLICATION_PREFIX;

@Controller
@RequestMapping(MVC_APPLICATION_PREFIX + "/map")
@RequiredArgsConstructor
public class MapController {
    private final MarkerController markerController;
    private final ContainerService containerService;
    private final UserServiceImpl userService;

    /**
     * Choice marker.
     *
     * @return add-marker.html
     */
    @GetMapping("/marker/choice")
    public String choiceMarker() {
        return "map/choose-marker";
    }

    @GetMapping("/markers/show")
    public String showMarker(@Parameter(hidden = true) @AuthenticationPrincipal UserModel userModel, Model model) {
        model.addAttribute("containers", userService.getAllUserContainers(userModel.getUsername()));
        return "map/show-markers";
    }

    @PostMapping("/container/show/enable")
    public String enableContainer(@RequestParam(name = "id") String containerId, @RequestParam(name = "name") String containerName, RedirectAttributes redirectAttrs) {
        containerService.enableContainer(containerId);
        redirectAttrs.addFlashAttribute("message", "The container " + containerName + " now enabled!");
        return "redirect:/app/map/markers/show";
    }

    @PostMapping("/container/show/disable")
    public String disableContainer(@RequestParam(name = "id") String containerId, @RequestParam(name = "name") String containerName, RedirectAttributes redirectAttrs) {
        containerService.disableContainer(containerId);
        redirectAttrs.addFlashAttribute("message", "The container " + containerName + " now disabled!");
        return "redirect:/app/map/markers/show";
    }

    @PostMapping("/container/show/delete")
    public String deleteContainer(@RequestParam(name = "id") String containerId, @RequestParam(name = "name") String containerName, RedirectAttributes redirectAttrs) {
        containerService.delete(containerId);
        redirectAttrs.addFlashAttribute("message", "The container " + containerName + " deleted!");
        return "redirect:/app/map/markers/show";
    }

    /**
     * Show add new marker form.
     *
     * @return add-new-marker.html
     */
    @GetMapping("/marker/new")
    public String showAddNewMarkerForm() {
        return "map/add-new-marker";
    }

    /**
     * Choice marker.
     *
     * @return ...
     */
    @PostMapping("/marker/new")
    public String addNewMarker(@Valid MarkerRequestDto markerRequestDto,
                               BindingResult markerErrors,
                               @Valid ContainerRequestDto containerRequestDto,
                               BindingResult containerErrors,
                               @RequestParam(name = "isBeSeen", required = false) Optional<String> isBeSeen,
                               @Parameter(hidden = true) @AuthenticationPrincipal UserModel userModel,
                               Model model) {
        containerRequestDto.setBeSeen(isBeSeen.isPresent());

        if (markerErrors.hasErrors() || containerErrors.hasErrors()) {
            model.addAttribute("marker_request_dto", markerRequestDto);
            model.addAttribute("container_request_dto", containerRequestDto);

            Map<String, String> errors = ControllerUtils.getErrorsMap(markerErrors);
            errors.putAll(ControllerUtils.getErrorsMap(containerErrors));

            model.mergeAttributes(errors);
            return "map/add-new-marker";
        }
        containerRequestDto.setMarker(markerRequestDto);

        if (containerRequestDto.isBeSeen()) {
            containerRequestDto.setUser(userModel);
        }

        containerService.add(WebLayerMapper.I.toContainerModel(containerRequestDto));

        return "redirect:" + MVC_APPLICATION_PREFIX + "/map/marker/choice";
    }

    /**
     * Choice marker.
     *
     * @return ...
     */
    @GetMapping("/marker/show")
    public String showMarkerGet(@RequestParam(name = "id") String containerId,
                                Model model) {
        model.addAttribute("container", containerService.getContainerById(containerId).get());
        return "map/show-marker";
    }

    @PostMapping("/marker/show")
    public String showMarkerPost(@Valid ContainerRequestDto containerRequestDto,
                                 BindingResult containerErrors,
                                 Model model) {
        if (containerErrors.hasErrors()) {
            model.addAttribute("container", containerRequestDto);

            model.mergeAttributes(ControllerUtils.getErrorsMap(containerErrors));
            return "map/show-marker";
        }

        ContainerModel container = containerService.getContainerById(containerRequestDto.getId()).get();

        container.setName(containerRequestDto.getName());
        container.setDescription(containerRequestDto.getDescription());
        container.setAddress(containerRequestDto.getAddress());

        MarkerModel marker = container.getMarker();

        marker.setName(containerRequestDto.getName());
        marker.setDescription(containerRequestDto.getDescription());
        marker.setAddress(containerRequestDto.getAddress());

        containerService.save(container);

        return "redirect:/app/map/markers/show";
    }
}
