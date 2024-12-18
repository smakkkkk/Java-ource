package com.example.postnews.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserRequest {

    @NotBlank(message = "Client name cannot be empty")
    @Size(min = 3, message = "Min size for category: {min}")
    private String username;

    @Email
    private String email;
}