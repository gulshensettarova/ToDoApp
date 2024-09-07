package com.company.toDoApp.dto.Request.Filter;

import lombok.Data;

@Data
public class UserFilterRequest {
    private int id;
    private String userEmail;
    private String userPassword;
    private boolean isConfirmed;
}
