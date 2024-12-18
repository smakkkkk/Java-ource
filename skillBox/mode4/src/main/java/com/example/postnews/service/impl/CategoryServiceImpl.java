package com.example.postnews.service.impl;

import com.example.postnews.entity.Category;
import com.example.postnews.repository.CategoryRepository;
import com.example.postnews.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        log.debug("CategoryServiceImpl -> findAll");
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(int pageNumber, int pageSize) {
        log.debug("CategoryServiceImpl -> findAll pageNumber= {}, pageSize= {}", pageNumber, pageSize);
        return categoryRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public Category findById(Long id) {
        log.debug("CategoryServiceImpl -> findById id= {}", id);
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category findByName(String name) {
        log.debug("CategoryServiceImpl -> findByName name= {}", name);
        return categoryRepository.findByName(name).orElse(null);
    }

    @Override
    public Category save(Category category) {
        log.debug("CategoryServiceImpl -> save category= {}", category);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        log.debug("CategoryServiceImpl -> update category= {}", category);
        Category existedCategory = categoryRepository.findById(category.getId()).orElse(null);
        if (existedCategory != null) {
            existedCategory.setName(category.getName());
            existedCategory.setPosts(category.getPosts());
            return categoryRepository.save(existedCategory);
        }
        return null;
    }

    @Override
    public Category deleteById(Long id) {
        log.debug("CategoryServiceImpl -> deleteById id= {}", id);
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            categoryRepository.deleteById(id);
            return category;
        }
        return null;
    }
}
