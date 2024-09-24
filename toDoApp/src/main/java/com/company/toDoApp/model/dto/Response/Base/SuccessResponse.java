package com.company.toDoApp.model.dto.Response.Base;

import com.company.toDoApp.model.enums.error.ResponseCodes;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SuccessResponse<T> extends BaseResponse<T>{
    private T value;
    public static <R>SuccessResponse<R> createBaseResponse(R data, ResponseCodes code){
     return SuccessResponse
             .<R>builder()
             .statusCode(code.getStatusCode())
             .message(code.getMessage())
             .value(data)
             .build();
    }

}
