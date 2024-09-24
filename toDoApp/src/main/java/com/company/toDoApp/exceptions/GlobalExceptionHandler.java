package com.company.toDoApp.exceptions;

import com.company.toDoApp.model.dto.Response.Base.ErrorResponse;
import com.company.toDoApp.model.enums.error.ResponseCodes;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
     public ResponseEntity<ErrorResponse> handleRuntimeExeptions(BaseException exception){
        exception.printStackTrace();
        ErrorResponse response=new ErrorResponse(exception.getMessage(),
                                                exception.getStatusCode());
        return ResponseEntity
                .status(HttpStatusCode.valueOf(exception.getHttpStatusCode()))
                .body(response);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExeptions(Exception exception){
        exception.printStackTrace();
        ErrorResponse response=new ErrorResponse(ResponseCodes.INTERNAL_SERVER_ERROR.getMessage(),
                                                 ResponseCodes.INTERNAL_SERVER_ERROR.getStatusCode());
        return ResponseEntity
                .status(HttpStatusCode.valueOf(ResponseCodes.INTERNAL_SERVER_ERROR.getHttpStatusCode()))
                .body(response);
    }



}
