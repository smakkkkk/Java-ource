package com.example.postnews.web.controller;

import com.example.postnews.entity.User;
import com.example.postnews.exception.EntityNotFoundException;
import com.example.postnews.mapper.UserMapper;
import com.example.postnews.service.UserService;
import com.example.postnews.web.request.UpsertUserRequest;
import com.example.postnews.web.response.error.ErrorResponse;
import com.example.postnews.web.response.user.UserResponse;
import com.example.postnews.web.response.user.UserListResponse;
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
@RequestMapping("/api/users")
@Tag(name = "User Controller")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @Operation(
            summary = "Get users",
            description = "Get all users and pagination(page number, page size)"
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = UserListResponse.class), mediaType = "application/json")
            }
    )
    @GetMapping
    public ResponseEntity<UserListResponse> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        Page<User> users = userService.findAll(pageNumber, pageSize);
        return ResponseEntity.ok(
                UserListResponse.builder()
                        .userResponseList(users.stream().map(userMapper::userToResponse).toList()).build());
    }

    @Operation(
            summary = "Get a user by his id",
            description = "Get a user by his id. return id, username and email"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
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
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(userMapper.userToResponse(user));
        }
        throw new EntityNotFoundException(MessageFormat.format("User with id = {0} not found", id));
    }

    @Operation(
            summary = "Create new user",
            description = "Create new user. Return id, username and email"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
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
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request) {
        User newUser = userService.save(userMapper.requestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToResponse(newUser));
    }

    @Operation(
            summary = "Update user by id",
            description = "Update user by his id. Return id, username and email"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json")
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
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId,
                                               @RequestBody @Valid UpsertUserRequest request) {
        User updateUser = userService.update(userMapper.requestToUser(userId, request));
        if (updateUser != null) {
            return ResponseEntity.ok(userMapper.userToResponse(updateUser));
        }
        throw new EntityNotFoundException(MessageFormat.format("User with id = {0} not found", userId));
    }

    @Operation(
            summary = "Removing a user by his id",
            description = "Removing a user by his id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        User deleteUser = userService.deleteById(id);
        if (deleteUser != null) {
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException(MessageFormat.format("User with id = {0} not found", id));
    }
}