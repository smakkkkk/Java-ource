package com.example.postnews.web.response.category;

import com.example.postnews.web.response.post.PostCategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;

    private String name;

    private List<PostCategoryResponse> posts = new ArrayList<>();
}