package com.example.postnews.mapper;

import com.example.postnews.entity.Post;
import com.example.postnews.web.request.UpsertPostRequest;
import com.example.postnews.web.response.post.PostFindAllResponse;
import com.example.postnews.web.response.post.PostResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(PostMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class})
public interface PostMapper {

    Post requestToPost(UpsertPostRequest request);

    @Mapping(source = "postId", target = "id")
    Post requestToPost(Long postId, UpsertPostRequest request);

    @Mapping(source = "user.username", target = "username")
    PostResponse postToResponse(Post post);

    @Mapping(source = "user.username", target = "username")
    PostFindAllResponse postFindAllToResponse(Post post);
}