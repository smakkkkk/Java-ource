package com.example.postnews;

import com.example.postnews.entity.Category;
import com.example.postnews.entity.Comment;
import com.example.postnews.entity.Post;
import com.example.postnews.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractTestController {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    protected User createUser(Long id, Comment comment, Post post) {

        User user = new User(id,"Name " + id,"Name"+id+"@mail.ru",new ArrayList<>(),new ArrayList<>());
        if (comment != null){
            user.addComment(comment);
        }
        if (post != null){
            user.addPost(post);
        }
        return user;
    }
    protected Comment createComment(Long id, User user, Post post) {
        return new Comment(id,"comment" +id, Instant.now(),Instant.now(),user,post);
    }
    protected Post createPost(Long id, Category category, User user) {
        return new Post(id,"tittle" + id,"description" + id,"body" + id,
                Instant.now(),Instant.now(),category,user,new ArrayList<>());
    }
    protected Category createCategory(Long id) {
        return new Category(id,"name" + id,new ArrayList<>());
    }


}
