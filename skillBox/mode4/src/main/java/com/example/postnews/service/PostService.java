package com.example.postnews.service;

import com.example.postnews.entity.Post;
import com.example.postnews.web.request.PostFilterRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    Page<Post> findAll(int pageNumber, int PageSize);

    Post findById(Long id);

    Post save(Post post);

    Post update(Post ost);

//    Post deleteById(Long id);

    Post deleteByIdAndUserId(Long id, Long userId);

    Page<Post> filterBy(PostFilterRequest filter);

}
