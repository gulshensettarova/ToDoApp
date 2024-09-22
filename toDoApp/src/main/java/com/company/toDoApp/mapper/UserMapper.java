package com.company.toDoApp.mapper;

import com.company.toDoApp.model.dao.entity.User;
import com.company.toDoApp.model.dto.Request.Create.UserCreateRequest;
import com.company.toDoApp.model.dto.Request.Filter.UserFilterRequest;
import com.company.toDoApp.model.dto.Request.Update.UserUpdateRequest;
import com.company.toDoApp.model.dto.Response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "userPassword", target = "userPasswordHash")
    User toEntity(UserCreateRequest user);

    @Mapping(source = "userPassword", target = "userPasswordHash")
    User toEntity(UserFilterRequest user);

    @Mapping(source = "userPassword", target = "userPasswordHash")
    User toEntity(UserUpdateRequest user);

    @Mapping(source = "userPasswordHash", target = "userPassword")
    UserResponse toDto(User user);

    @Mapping(source = "userPasswordHash", target = "userPassword")
    List<UserResponse> toDto(List<User> users);
}
