package com.example.postnews.service;

import com.example.postnews.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Page<Category> findAll(int pageNumber, int pageSize);

    Category findById(Long id);

    Category findByName(String name);

    Category save(Category category);

    Category update(Category category);

    Category deleteById(Long id);
}
