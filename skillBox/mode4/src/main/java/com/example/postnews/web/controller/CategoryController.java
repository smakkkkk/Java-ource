package com.example.postnews.web.controller;

import com.example.postnews.entity.Category;
import com.example.postnews.entity.Post;
import com.example.postnews.exception.EntityNotFoundException;
import com.example.postnews.mapper.CategoryMapper;
import com.example.postnews.service.CategoryService;
import com.example.postnews.web.request.UpsertCategoryRequest;
import com.example.postnews.web.response.category.CategoryListResponse;
import com.example.postnews.web.response.category.CategoryResponse;
import com.example.postnews.web.response.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@Tag(name = "Category Controller")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @Operation(
            summary = "Get categories",
            description = "Get all categories and pagination(page number, page size)"
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = CategoryListResponse.class), mediaType = "application/json")
            }
    )
    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        Page<Category> categories = categoryService.findAll(pageNumber, pageSize);
        return ResponseEntity.ok(
                CategoryListResponse.builder()
                        .categoryResponseList(categories.stream().map(categoryMapper::categoryFindAllToResponse).toList())
                        .build()
        );
    }

    @Operation(
            summary = "Get a category by his id",
            description = "Get a category by his id. return id, category name and list posts"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category != null) {
            List<Post> postList = category.getPosts();
            if (postList != null && !postList.isEmpty()) {
                for (Post post : postList) {
                    post.setCategory(null);
                    post.setComments(null);
                    post.setUser(null);
                }
            }
            return ResponseEntity.ok(categoryMapper.categoryToResponse(category));
        }
        throw new EntityNotFoundException(MessageFormat.format("Category with id = {0} not found", id));
    }

    @Operation(
            summary = "Create new category",
            description = "Create new category. Return id, category name and list posts"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid UpsertCategoryRequest request) {
        Category newCategory = categoryService.save(categoryMapper.requestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.categoryToResponse(newCategory));
    }

    @Operation(
            summary = "Update category by id",
            description = "Update category by his ID. Return id, category name and list posts"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CategoryResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long categoryId,
                                                   @RequestBody @Valid UpsertCategoryRequest request) {
        Category updateCategory = categoryService.update(categoryMapper.requestToCategory(categoryId, request));
        if (updateCategory != null) {
            return ResponseEntity.ok(categoryMapper.categoryToResponse(updateCategory));
        }
        throw new EntityNotFoundException(MessageFormat.format("Category with id = {0} not found", categoryId));
    }

    @Operation(
            summary = "Removing a category by his id",
            description = "Removing a category by his id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Category deleteCategory = categoryService.deleteById(id);
        if (deleteCategory != null) {
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException(MessageFormat.format("Category with id = {0} not found", id));
    }
}