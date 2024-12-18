package com.example.postnews.web.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCategoryRequest {

    @Size(min = 3, max = 20, message = "Min size for category: {min} Max size is: {max}")
    private String name;
}