package com.company.toDoApp.model.dto.Request.Create;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String userEmail;
    private String userPassword;
    private boolean isConfirmed;
}
