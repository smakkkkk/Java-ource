package com.example.postnews.web.controller;

import com.example.postnews.entity.Comment;
import com.example.postnews.exception.EntityNotFoundException;
import com.example.postnews.mapper.CommentMapper;
import com.example.postnews.service.CommentService;
import com.example.postnews.web.request.UpsertCommentRequest;
import com.example.postnews.web.response.comment.CommentResponse;
import com.example.postnews.web.response.comment.CommentListResponse;
import com.example.postnews.web.response.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post/comments")
@Tag(name = "Comment Controller")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Operation(
            summary = "Get All comments by id post",
            description = "Get all comments by id post. Return list comments by post"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/{postId}")
    public ResponseEntity<CommentListResponse> findByPostId(@PathVariable("postId") Long postId) {

        List<Comment> comments = commentService.findAllByPostId(postId);
        if (comments != null) {
            return ResponseEntity.ok(
                    CommentListResponse.builder()
                            .commentResponseList(comments.stream().map(commentMapper::commentToResponse)
                                    .toList()).build()
            );
        }
        throw new EntityNotFoundException(MessageFormat.format("No comments found for post with id= {0}", postId));
    }

    @Operation(
            summary = "Create new comment",
            description = "Create new comment. Return id, comment, username and createAt"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
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
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request) {
        Comment newComment = commentService.save(commentMapper.requestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToResponse(newComment));

    }

    @Operation(
            summary = "Update comment by id",
            description = "Update comment by id. Return id, comment, username and createAt"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")
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
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId,
                                                  @RequestBody @Valid UpsertCommentRequest request) {
        Comment updateComment = commentService.update(commentMapper.requestToComment(commentId, request));
        if (updateComment != null) {
            return ResponseEntity.ok(commentMapper.commentToResponse(updateComment));
        }
        throw new EntityNotFoundException(MessageFormat.format("Comment with id= {0} not found", commentId));
    }

    @Operation(
            summary = "Removing comment by id",
            description = "Removing comment by id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestParam("userId") Long userid) {
        commentService.deleteByIdAndUserId(id, userid);
        return ResponseEntity.noContent().build();
    }
}