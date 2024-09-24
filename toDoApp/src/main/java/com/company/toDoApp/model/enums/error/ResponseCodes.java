package com.company.toDoApp.model.enums.error;

import lombok.Getter;

@Getter
public enum ResponseCodes {
    SUCCESS("Operation successful",200,200),
    INTERNAL_SERVER_ERROR("Internal server error",500,500),
    TASK_NOT_FOUND("Task not found",400,400),
    TEAMLEADER_NOT_FOUND("Team leader not found for custom project",400,400),
    EMPLOYEE_NOT_FOUND("employee  not found",400,400);

    private final String message;
    private final Integer statusCode;
    private final Integer httpStatusCode;

    ResponseCodes(String message, Integer statusCode, Integer httpStatusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.httpStatusCode = httpStatusCode;
    }
}
