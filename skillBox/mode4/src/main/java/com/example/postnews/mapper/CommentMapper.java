package com.example.postnews.mapper;

import com.example.postnews.entity.Comment;
import com.example.postnews.web.request.UpsertCommentRequest;
import com.example.postnews.web.response.comment.CommentResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment requestToComment(UpsertCommentRequest request);

    @Mapping(source = "commentId", target = "id")
    Comment requestToComment(Long commentId, UpsertCommentRequest request);

    @Mapping(source = "user.username", target = "username")
    CommentResponse commentToResponse(Comment comment);
}