package com.example.pathfinderd.service;

import com.example.pathfinderd.model.entity.Category;
import com.example.pathfinderd.model.entity.enums.CategoryNameEnum;

public interface CategoryService {
    Category findCategoryByName(CategoryNameEnum categoryNameEnum);
}
