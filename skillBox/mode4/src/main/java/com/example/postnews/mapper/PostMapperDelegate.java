package com.example.postnews.mapper;

import com.example.postnews.entity.Category;
import com.example.postnews.entity.Post;
import com.example.postnews.service.CategoryService;
import com.example.postnews.service.CommentService;
import com.example.postnews.service.UserService;
import com.example.postnews.web.request.UpsertPostRequest;
import com.example.postnews.web.response.post.PostFindAllResponse;
import com.example.postnews.web.response.post.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class PostMapperDelegate implements PostMapper {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CommentMapper commentMapper;


    @Override
    public Post requestToPost(UpsertPostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setBody(request.getBody());
        Category category = categoryService.findByName(request.getCategoryName());
        if (category == null) {
            category = new Category();
            category.setName(request.getCategoryName());
            category = categoryService.save(category);
        }
        post.setCategory(category);
        post.setUser(userService.findById(request.getUserId()));
        post.setComments(commentService.findAllByUserId(request.getUserId()));
        return post;
    }

    @Override
    public PostFindAllResponse postFindAllToResponse(Post post) {
        PostFindAllResponse response = postMapper.postFindAllToResponse(post);
        response.setCountComment(post.getComments().size());
        response.setUsername(post.getUser().getUsername());
        return response;
    }

    @Override
    public PostResponse postToResponse(Post post) {
        PostResponse response = postMapper.postToResponse(post);
        response.setCountComment(post.getComments().size());
        response.setUsername(post.getUser().getUsername());
        response.setComments(post.getComments().stream()
                .map(it -> commentMapper.commentToResponse(it))
                .toList());

        return response;

    }

    @Override
    public Post requestToPost(Long postId, UpsertPostRequest request) {
        Post post = requestToPost(request);
        post.setId(postId);
        return post;
    }
}

