package com.company.toDoApp.exceptions;

import com.company.toDoApp.model.enums.error.ResponseCodes;
import lombok.*;

@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException{
    private String message;
    private Integer statusCode;
    private Integer httpStatusCode;
    public static BaseException of(ResponseCodes codes){
        return new BaseException
                (codes.getMessage(),
                 codes.getStatusCode(),
                 codes.getHttpStatusCode());
    }

}
