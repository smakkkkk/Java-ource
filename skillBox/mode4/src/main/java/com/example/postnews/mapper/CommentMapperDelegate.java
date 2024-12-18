package com.example.postnews.mapper;

import com.example.postnews.entity.Comment;
import com.example.postnews.service.PostService;
import com.example.postnews.service.UserService;
import com.example.postnews.web.request.UpsertCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setComment(request.getComment());
        comment.setUser(userService.findById(request.getUserId()));
        comment.setPost(postService.findById(request.getPostId()));
        return comment;
    }

    @Override
    public Comment requestToComment(Long commentId, UpsertCommentRequest request) {
        Comment comment = requestToComment(request);
        comment.setId(commentId);
        return comment;
    }
}