package com.example.postnews.web.controller;

import com.example.postnews.entity.Post;
import com.example.postnews.exception.EntityNotFoundException;
import com.example.postnews.mapper.PostMapper;
import com.example.postnews.service.PostService;
import com.example.postnews.web.request.PostFilterRequest;
import com.example.postnews.web.request.UpsertPostRequest;
import com.example.postnews.web.response.error.ErrorResponse;
import com.example.postnews.web.response.post.PostListResponse;
import com.example.postnews.web.response.post.PostResponse;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category/posts")
@Tag(name = "Post Controller")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @Operation(
            summary = "Filtering posts by categories and username",
            description = "Filtering posts by categories and username"
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = PostListResponse.class), mediaType = "application/json")
            }
    )
    @GetMapping("/filter")
    public ResponseEntity<PostListResponse> filterBy(PostFilterRequest filterRequest) {
        Page<Post> posts = postService.filterBy(filterRequest);
        return ResponseEntity.ok(
                PostListResponse.builder()
                        .totalCount(posts.getTotalElements())
                        .postResponseList(posts.stream().map(postMapper::postFindAllToResponse).toList())
                        .build()
        );
    }

    @Operation(
            summary = "Get all posts",
            description = "Get all posts and pagination(page number, page size)"
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = PostListResponse.class), mediaType = "application/json")
            }
    )
    @GetMapping
    public ResponseEntity<PostListResponse> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                    @RequestParam(defaultValue = "10") int pageSize) {

        Page<Post> posts = postService.findAll(pageNumber, pageSize);
        return ResponseEntity.ok(
                PostListResponse.builder()
                        .totalCount(posts.getTotalElements())
                        .postResponseList(posts.stream().map(postMapper::postFindAllToResponse).toList())
                        .build()
        );
    }

    @Operation(
            summary = "Get post bu id",
            description = "Get post by id. Return tittle, description, body, createAt, updateAt, count comment and list comments"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = PostResponse.class), mediaType = "application/json")
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
    public ResponseEntity<PostResponse> findById(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        if (post != null) {
            return ResponseEntity.ok(postMapper.postToResponse(post));
        }
        throw new EntityNotFoundException(MessageFormat.format("Post with id = {0} not found", id));
    }

    @Operation(
            summary = "Create new post",
            description = "create new post. Return tittle, description, body, createAt, updateAt, count comment and list comments"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = PostResponse.class), mediaType = "application/json")
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
    public ResponseEntity<PostResponse> create(@RequestBody @Valid UpsertPostRequest request) {
        Post newPost = postService.save(postMapper.requestToPost(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.postToResponse(newPost));
    }

    @Operation(
            summary = "Update post by id",
            description = "Update post by id. Return tittle, description, body, createAt, updateAt, count comment and list comments"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = PostResponse.class), mediaType = "application/json")
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
    public ResponseEntity<PostResponse> update(@PathVariable("id") Long postId,
                                               @RequestBody @Valid UpsertPostRequest request) {
        Post updatePost = postService.update(postMapper.requestToPost(postId, request));
        if (updatePost != null) {
            return ResponseEntity.ok(postMapper.postToResponse(updatePost));
        }
        throw new EntityNotFoundException(MessageFormat.format("Post with id = {0} not found", postId));
    }

    @Operation(
            summary = "Removing post by id",
            description = "Removing post by id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestParam("userId") Long userId) {
        Post deletePost = postService.deleteByIdAndUserId(id,userId);
        if (deletePost != null) {
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException(MessageFormat.format("Post with id = {0} not found", id));
    }
}