package com.example.pathfinderd.web;

import com.example.pathfinderd.model.binding.RouteAddBindingModel;
import com.example.pathfinderd.model.service.RoutServiceModel;
import com.example.pathfinderd.model.view.RouteViewModel;
import com.example.pathfinderd.repository.RoutRepository;
import com.example.pathfinderd.service.RoutService;
import com.example.pathfinderd.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/routes")
public class RoutController {

    private final RoutService routService;
    private final CurrentUser currentUser;
    private final RoutRepository routRepository;
    private final ModelMapper modelMapper;

    public RoutController(RoutService routService, CurrentUser currentUser, RoutRepository routRepository, ModelMapper modelMapper) {
        this.routService = routService;
        this.currentUser = currentUser;
        this.routRepository = routRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public String allRouts(Model model){
        List<RouteViewModel> routeViewModelList = routService.findAllRoutViews();
        model.addAttribute("routes", routeViewModelList);

        return "routes";
    }

    @GetMapping("/add")
    public String add(){
        if (currentUser.getId() == null){
            return "redirect:/users/login";
        }
        return "add-route";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model){
        model.addAttribute("route", routService.findRouteById(id));



        return "route-details";
    }

    @PostMapping("/add")
    public String addConfirmation(@Valid RouteAddBindingModel routeAddBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("routeAddBindingModel", routeAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.routeAddBindingModel", bindingResult);

            return "redirect:add";
        }

        RoutServiceModel routServiceModel =
                modelMapper.map(routeAddBindingModel, RoutServiceModel.class);

        routServiceModel.setGpxCoordinates(
                new String(routeAddBindingModel.getGpxCoordinates().getBytes()));

        routService.addNewRoute(routServiceModel);

        return "redirect:all";
    }

    @ModelAttribute
    public RouteAddBindingModel routeAddBindingModel(){
        return new RouteAddBindingModel();
    }



}
