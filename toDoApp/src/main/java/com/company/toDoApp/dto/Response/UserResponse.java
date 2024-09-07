package com.company.toDoApp.dto.Response;

import lombok.Data;

@Data
public class UserResponse {
    private int id;
    private String userEmail;
    private String userPassword;
    private boolean isConfirmed;
}
