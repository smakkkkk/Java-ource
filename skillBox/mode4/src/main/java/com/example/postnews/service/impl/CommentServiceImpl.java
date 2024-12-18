package com.example.postnews.service.impl;

import com.example.postnews.aop.AccessibleDeleteComment;
import com.example.postnews.aop.AccessibleUpdateComment;
import com.example.postnews.entity.Comment;
import com.example.postnews.entity.Post;
import com.example.postnews.entity.User;
import com.example.postnews.repository.CommentRepository;
import com.example.postnews.service.CommentService;
import com.example.postnews.service.PostService;
import com.example.postnews.service.UserService;
import com.example.postnews.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final PostService postService;


    @Override
    public List<Comment> findAll() {
        log.debug("CommentServiceImpl -> findAll");
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        log.debug("CommentServiceImpl -> findById id= {}", id);
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comment> findAllByUserId(Long userId) {
        log.debug("CommentServiceImpl -> findAllByUserId userId= {}", userId);
        List<Comment> comments = commentRepository.findAllByUserId(userId);
        if (comments != null) {
            return comments;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        log.debug("CommentServiceImpl -> findAllByNewsId postId= {}", postId);
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public Comment save(Comment comment) {
        log.debug("CommentServiceImpl -> save comment= {}", comment);
        User user = userService.findById(comment.getUser().getId());
        Post post = postService.findById(comment.getPost().getId());
        user.addComment(comment);
        post.addComment(comment);
        return commentRepository.save(comment);
    }

    @Override
    @AccessibleUpdateComment
    public Comment update(Comment comment) {
        log.debug("CommentServiceImpl -> update comment= {}", comment);
        User user = userService.findById(comment.getUser().getId());
        Post post = postService.findById(comment.getPost().getId());
        Comment existedComment = findById(comment.getId());

        BeanUtils.copyNonNullProperties(comment, existedComment);
        existedComment.setUser(user);
        existedComment.setPost(post);
        return commentRepository.save(existedComment);
    }

//    @Override
//    @AccessibleDeleteComment
//    public Comment deleteById(Long id) {
//        log.debug("CommentServiceImpl -> deleteById id= {}", id);
//        Comment comment = commentRepository.findById(id).orElse(null);
//        if (comment != null) {
//            commentRepository.deleteById(id);
//            return comment;
//        }
//        return null;
//    }

    @Override
    @Transactional
    @AccessibleDeleteComment
    public Comment deleteByIdAndUserId(Long id, Long userId) {
        log.debug("CommentServiceImpl -> deleteByIdAndUserId id= {}, userId= {}", id, userId);
        Comment deletedComment = commentRepository.findById(id).orElse(null);
        commentRepository.deleteByIdAndUserId(id, userId);
        return deletedComment;
    }

    @Override
    public Long countAllByNewsId(Long postId) {
        log.debug("CommentServiceImpl -> countAllByPostId PostId= {}", postService);
        return commentRepository.countAllByPostId(postId);
    }
}