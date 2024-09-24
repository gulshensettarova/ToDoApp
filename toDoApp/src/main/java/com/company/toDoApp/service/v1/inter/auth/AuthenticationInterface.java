package com.company.toDoApp.service.v1.inter.auth;

import com.company.toDoApp.model.dto.Request.Filter.SignInRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationInterface {
    String singInAndReturnJWT(SignInRequest request);
    void signOut(HttpServletRequest request);
}
