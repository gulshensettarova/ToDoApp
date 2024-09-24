package com.company.toDoApp.service.v1.inter.user;

import com.company.toDoApp.model.dao.entity.User;
import com.company.toDoApp.model.dto.Request.Create.UserCreateRequest;

public interface UserRegistrationInterface {
     boolean registerUser(UserCreateRequest request);
     User findByUserId(int userId);
    User findByUserEmail(String email);
    void checkUserAlreadyExistOrNot(String email);
    String getRoleByEmail(String email);
}
