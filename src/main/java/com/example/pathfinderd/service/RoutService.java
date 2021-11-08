package com.example.pathfinderd.service;

import com.example.pathfinderd.model.service.RoutServiceModel;
import com.example.pathfinderd.model.view.RouteDetailsViewModel;
import com.example.pathfinderd.model.view.RouteViewModel;

import java.util.List;

public interface RoutService {
    List<RouteViewModel> findAllRoutViews();

    void addNewRoute(RoutServiceModel routServiceModel);

    RouteDetailsViewModel findRouteById(Long id);
}
