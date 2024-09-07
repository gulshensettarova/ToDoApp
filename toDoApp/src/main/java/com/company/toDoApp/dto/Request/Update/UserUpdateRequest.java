package com.company.toDoApp.dto.Request.Update;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private int id;
    private String userEmail;
    private String userPassword;
    private boolean isConfirmed;
}
