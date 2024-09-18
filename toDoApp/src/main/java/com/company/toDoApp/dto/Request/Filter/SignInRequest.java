package com.company.toDoApp.dto.Request.Filter;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
