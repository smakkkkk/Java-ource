package com.example.postnews.mapper;

import com.example.postnews.entity.Category;
import com.example.postnews.web.request.UpsertCategoryRequest;
import com.example.postnews.web.response.category.CategoryFindAllResponse;
import com.example.postnews.web.response.category.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PostMapper.class})
public interface CategoryMapper {

    Category requestToCategory(UpsertCategoryRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, UpsertCategoryRequest request);

    CategoryResponse categoryToResponse(Category category);

    CategoryFindAllResponse categoryFindAllToResponse(Category category);

}