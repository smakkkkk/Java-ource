package com.example.postnews.mapper;

import com.example.postnews.entity.User;
import com.example.postnews.web.request.UpsertUserRequest;
import com.example.postnews.web.response.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PostMapper.class, CommentMapper.class})
public interface UserMapper {

    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest request);

    UserResponse userToResponse(User user);
}