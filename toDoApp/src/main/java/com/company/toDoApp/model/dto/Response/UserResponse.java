package com.company.toDoApp.model.dto.Response;

import lombok.Data;

@Data
public class UserResponse {
    private int id;
    private String userEmail;
    private String userPassword;
    private boolean isConfirmed;
}
