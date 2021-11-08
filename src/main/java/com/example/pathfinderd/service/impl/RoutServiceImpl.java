package com.example.pathfinderd.service.impl;

import com.example.pathfinderd.model.entity.Route;
import com.example.pathfinderd.model.service.RoutServiceModel;
import com.example.pathfinderd.model.view.RouteDetailsViewModel;
import com.example.pathfinderd.model.view.RouteViewModel;
import com.example.pathfinderd.repository.RoutRepository;
import com.example.pathfinderd.service.CategoryService;
import com.example.pathfinderd.service.RoutService;
import com.example.pathfinderd.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoutServiceImpl implements RoutService {
    private final RoutRepository routRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;

    public RoutServiceImpl(RoutRepository routRepository, ModelMapper modelMapper, UserService userService, CategoryService categoryService) {
        this.routRepository = routRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public List<RouteViewModel> findAllRoutViews() {
        return routRepository
                .findAll()
                .stream().map(route -> {
                    RouteViewModel routeViewModel = modelMapper.map(route, RouteViewModel.class);
                    if (route.getPictures().isEmpty()){
                        routeViewModel.setPictureUrl("images/pic4.jpg");
                    }else {
                        routeViewModel
                                .setPictureUrl(route
                                        .getPictures()
                                        .stream()
                                        .findAny()
                                        .get()
                                        .getUrl());
                    }

                    return routeViewModel;

                }).collect(Collectors.toList());


    }

    @Override
    public void addNewRoute(RoutServiceModel routServiceModel) {
        Route route = modelMapper.map(routServiceModel, Route.class);
        route.setAuthor(userService.findCurrentLoginUserEntity());
        route.setCategories(routServiceModel
                .getCategories()
                .stream()
                .map(categoryService::findCategoryByName)
                .collect(Collectors.toSet()));

        routRepository.save(route);
    }

    @Override
    public RouteDetailsViewModel findRouteById(Long id) {
        return routRepository
                .findById(id)
                .map(route -> modelMapper.map(route, RouteDetailsViewModel.class))
                .orElse(null);

    }
}
