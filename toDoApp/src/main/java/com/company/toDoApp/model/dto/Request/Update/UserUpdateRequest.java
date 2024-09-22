package com.company.toDoApp.model.dto.Request.Update;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private int id;
    private String userEmail;
    private String userPassword;
    private boolean isConfirmed;
}
