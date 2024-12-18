package com.example.postnews.repository;

import com.example.postnews.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUserId(Long userId);

    List<Comment> findAllByPostId(Long newsId);

    Long countAllByPostId(Long postId);

    void deleteByIdAndUserId(Long id, Long userId);
}