package com.example.postnews.aop;

import com.example.postnews.entity.Post;
import com.example.postnews.exception.AccessibleException;
import com.example.postnews.service.PostService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UpdatePostAspect {
    private final PostService postService;

    @Around("@annotation(AccessibleUpdatePost)")
    public Object userControlUpdateNewsAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        Post updatePost = (Post) args[0];
        Long updateUserId = updatePost.getUser().getId();
        Long updatePostId = updatePost.getId();
        Post post = postService.findById(updatePostId);
        if (post.getUser().getId() == updateUserId) {
            return proceedingJoinPoint.proceed(args);
        }
        throw new AccessibleException("User is not allowed to update this post");
    }
}