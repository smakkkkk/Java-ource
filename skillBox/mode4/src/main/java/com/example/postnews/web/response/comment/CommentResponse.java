package com.example.postnews.web.response.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long id;

    private String comment;

    private String username;

    private Instant createdAt;

}