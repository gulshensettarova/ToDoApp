package com.company.toDoApp.model.dto.Response.Base;

public class ErrorResponse extends BaseResponse<Object>{
    public ErrorResponse(String message,Integer statusCode){

        super(message,statusCode);
    }
}
