package com.example.postnews.repository.specification;

import com.example.postnews.entity.Category;
import com.example.postnews.entity.Post;
import com.example.postnews.entity.User;
import com.example.postnews.web.request.PostFilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public interface PostSpecification {

    static Specification<Post> withFilter(PostFilterRequest filter) {
        return Specification.where(
                        byCategoryName(filter.getCategoryName()))
                .and(byUsername(filter.getUsername()));
    }

    static Specification<Post> byCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(categoryName)) {
                return null;
            }

            return criteriaBuilder.equal(root.get(Post.Fields.category).get(Category.Fields.name), categoryName);
        };
    }

    static Specification<Post> byUsername(String username) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(username)) {
                return null;
            }
            return criteriaBuilder.equal(root.get(Post.Fields.user).get(User.Fields.username), username);
        };
    }
}