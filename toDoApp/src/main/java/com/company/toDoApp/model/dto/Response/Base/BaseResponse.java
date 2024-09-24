package com.company.toDoApp.model.dto.Response.Base;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class BaseResponse<T> {
    private String message;
    private Integer statusCode;
}