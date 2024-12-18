package com.example.postnews.service;

import com.example.postnews.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    Comment findById(Long id);

    List<Comment> findAllByUserId(Long userId);

    List<Comment> findAllByPostId(Long newsId);

    Comment save(Comment comment);

    Comment update(Comment comment);

//    Comment deleteById(Long id);

    Long countAllByNewsId(Long newsId);

    Comment deleteByIdAndUserId(Long id, Long userId);

}