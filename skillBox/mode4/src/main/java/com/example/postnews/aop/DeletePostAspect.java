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
public class DeletePostAspect {
    private final PostService postService;

    @Around("@annotation(AccessibleDeletePost)")
    public Object userControlDeleteNewsAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        Long postId = (Long) args[0];
        Long userId = (Long) args[1];
        Post deletingPost = postService.findById(postId);
        if (deletingPost.getUser().getId() == userId) {
            return proceedingJoinPoint.proceed(args);
        }
        throw new AccessibleException("User is not allowed to delete this post");
    }
}
