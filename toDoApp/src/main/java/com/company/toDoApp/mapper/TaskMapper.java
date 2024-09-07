package com.company.toDoApp.mapper;

import com.company.toDoApp.dao.entity.Task;
import com.company.toDoApp.dto.Request.Create.TaskCreateRequest;
import com.company.toDoApp.dto.Request.Filter.TaskFilterRequest;
import com.company.toDoApp.dto.Request.Update.TaskUpdateRequest;
import com.company.toDoApp.dto.Response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface TaskMapper {

    List<TaskResponse> toDto(List<Task> tasks);
    TaskResponse toDto(Task task);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "projectId", target = "project.id")
    @Mapping(source = "statusId", target = "status.id")
    @Mapping(source = "employeeId", target = "employee.id")
    Task toEntity(TaskCreateRequest request);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "projectId", target = "project.id")
    @Mapping(source = "statusId", target = "status.id")
    @Mapping(source = "employeeId", target = "employee.id")
    Task toEntity(TaskFilterRequest request);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "projectId", target = "project.id")
    @Mapping(source = "statusId", target = "status.id")
    @Mapping(source = "employeeId", target = "employee.id")
    Task toEntity(TaskUpdateRequest request);
}

