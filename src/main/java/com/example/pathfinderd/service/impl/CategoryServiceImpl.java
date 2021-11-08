package com.example.pathfinderd.service.impl;

import com.example.pathfinderd.model.entity.Category;
import com.example.pathfinderd.model.entity.enums.CategoryNameEnum;
import com.example.pathfinderd.repository.CategoryRepository;
import com.example.pathfinderd.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryByName(CategoryNameEnum categoryNameEnum) {
        return categoryRepository.findByName(categoryNameEnum).orElse(null);
    }




}
