package com.example.postnews.web.response.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryListResponse {

    private List<CategoryFindAllResponse> categoryResponseList = new ArrayList<>();
}
