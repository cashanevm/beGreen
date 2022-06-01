package ua.knu.beGreen.web.controller.mvc;

import ua.knu.beGreen.persistence.entity.PointEntity;
import ua.knu.beGreen.persistence.repository.PointRepository;
import ua.knu.beGreen.service.api.ContainerService;
import ua.knu.beGreen.service.api.impl.UserServiceImpl;
import ua.knu.beGreen.service.model.ContainerModel;
import ua.knu.beGreen.service.model.UserModel;
import ua.knu.beGreen.web.dto.request.KgRequestDto;
import ua.knu.beGreen.web.dto.request.СoefficientRequestDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import static ua.knu.beGreen.service.constants.ApplicationConstants.MVC_APPLICATION_PREFIX;

@Controller
@RequestMapping(MVC_APPLICATION_PREFIX + "/pointing")
@RequiredArgsConstructor
public class PointingController {
    private static final String NOT_ENOUGH_MONEY_MSG = "not enough money at least required: %s $";
    private static final String NOT_FOUND_MSG = "not found";
    private static final String ADDED_KGS_MSG = "Added %s to %s!";


    private final ContainerService containerService;
    private final HomeController controller;
    private final PointRepository pointRepository;
    private final UserServiceImpl userService;

    @GetMapping("/show")
    public String showPointing(@RequestParam(name = "id") String containerId,
                               Model model) {
        model.addAttribute("requestDto", new СoefficientRequestDto());
        model.addAttribute("container", containerService.getContainerById(containerId).get());
        return "pointing/show-marker-pointing";
    }

    @PostMapping("/add")
    public String addPointing(@RequestParam(name = "id") String containerId,
                              @Valid СoefficientRequestDto requestDto,
                              BindingResult bindingResult, Model model,
                              @Parameter(hidden = true) @AuthenticationPrincipal UserModel userModel) {
        ContainerModel containerModel = containerService.getContainerById(containerId).get();

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrorsMap(bindingResult);

            model.mergeAttributes(errors);
            model.addAttribute("container", containerModel);
            model.addAttribute("requestDto", requestDto);
            return "pointing/show-marker-pointing";
        }

        Double price = 1 * containerModel.getSize() * requestDto.getCoefficient();

        if (userModel.getCurrency() == null || userModel.getCurrency() < price.intValue()) {
            model.addAttribute("container", containerModel);
            model.addAttribute("requestDto", requestDto);
            model.addAttribute("message", String.format(NOT_ENOUGH_MONEY_MSG, price));
            return "pointing/show-marker-pointing";
        }

        userModel.setCurrency(userModel.getCurrency() - price.intValue());
        containerModel.setPoint(true);

        containerModel.setPointKef(requestDto.getCoefficient());

        userService.saveUser(userModel);
        containerService.save(containerModel);

        model.addAttribute("container", containerModel);

        return "pointing/show-marker-pointing";
    }

    @PostMapping("/complete")
    public String completePointing(@RequestParam(name = "id") String containerId,
                                   Model model) {
        ContainerModel containerModel = containerService.getContainerById(containerId).get();

        containerModel.setPoint(false);

        Map<String, List<Integer>> userKgs = new HashMap<>();

        List<PointEntity> pointRecords = pointRepository.findByContainerId(containerModel.getId());

        pointRecords.forEach(pointRecord -> {
            if (userKgs.containsKey(pointRecord.getUserId())) {
                userKgs.get(pointRecord.getUserId()).add(pointRecord.getKilogram());
            } else {
                List<Integer> kgs = new ArrayList<>();
                kgs.add(pointRecord.getKilogram());
                userKgs.put(pointRecord.getUserId(), kgs);
            }
        });

        Integer allKgs = userKgs.values().stream()
                .reduce(new ArrayList<>(), (a, b) -> {a.addAll(b);return a;})
                .stream()
                .reduce(0, Integer::sum);

        Double onePercent = allKgs / 100.0;

        userKgs.forEach((userId, userKg) -> {
            Integer kgs = userKg.stream().reduce(0, Integer::sum);
            Double percent = kgs / onePercent;
            Integer points = ((Double) (containerModel.getSize() * containerModel.getPointKef() * percent)).intValue();

            Optional<UserModel> optionalOfUser = userService.findById(userId);

            if (optionalOfUser.isPresent()) {
                UserModel user = optionalOfUser.get();
                user.setPoint(user.getPoint() == null ? points : user.getPoint() + points);
                userService.saveUser(user);
            }
        });

        containerModel.setContents(0);

        pointRepository.deleteAll(pointRecords);
        containerService.save(containerModel);

        model.addAttribute("requestDto", new СoefficientRequestDto());
        model.addAttribute("container", containerModel);

        return "pointing/show-marker-pointing";
    }

    @GetMapping("/add/kg/container/{id}")
    public String getAddKgToContainerHtml(@PathVariable String id,
                                          Model model,
                                          @Parameter(hidden = true) @AuthenticationPrincipal UserModel userModel) {
        Optional<ContainerModel> containerModel = containerService.getContainerById(id);

        if (containerModel.isPresent()) {
            model.addAttribute("container", containerModel.get());
            model.addAttribute("user", userModel);
            model.addAttribute("requestDto", new KgRequestDto());
        } else {
            model.addAttribute("user", userModel);
            model.addAttribute("message", NOT_FOUND_MSG);
        }
        return "pointing/add-kg-pointing";
    }

    @PostMapping("/add/kg/container")
    public String addKgToContainer(@RequestParam(name = "id") String containerId,
                                   Model model,
                                   @Parameter(hidden = true) @AuthenticationPrincipal UserModel userModel,
                                   @Valid KgRequestDto requestDto, BindingResult bindingResult) {
        Optional<ContainerModel> containerModel = containerService.getContainerById(containerId);

        if (containerModel.isPresent()) {
            if (bindingResult.hasErrors()) {
                Map<String, String> errors = ControllerUtils.getErrorsMap(bindingResult);

                model.mergeAttributes(errors);
                model.addAttribute("container", containerModel.get());
                model.addAttribute("user", userModel);
                model.addAttribute("requestDto", new KgRequestDto());
                return "pointing/add-kg-pointing";
            } else {
                //do
                ContainerModel container = containerModel.get();
                container.setContents(container.getContents() + requestDto.getKg());

                model.addAttribute("message", String.format(ADDED_KGS_MSG,  requestDto.getKg(), container.getName()));

                pointRepository.save(PointEntity.builder()
                        .containerId(container.getId())
                        .kilogram(requestDto.getKg())
                        .userId(userModel.getId())
                        .build());

                containerService.save(container);
                return controller.greeting(model, userModel);
            }
        } else {
            model.addAttribute("message", NOT_FOUND_MSG);
            return "pointing/add-kg-pointing";
        }

    }
}
