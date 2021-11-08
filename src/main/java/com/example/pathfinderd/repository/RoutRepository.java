package com.example.pathfinderd.repository;

import com.example.pathfinderd.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutRepository extends JpaRepository<Route, Long> {

}
