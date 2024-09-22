package com.company.toDoApp.mapper;

import com.company.toDoApp.model.dao.entity.Employee;
import com.company.toDoApp.model.dto.Request.Create.EmployeeCreateRequest;
import com.company.toDoApp.model.dto.Request.Filter.EmployeeFilterRequest;
import com.company.toDoApp.model.dto.Request.Update.EmployeeUpdateRequest;
import com.company.toDoApp.model.dto.Response.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "teamName",source = "team")
    @Mapping(target = "professionName",source = "profession")
    @Mapping(source = "user", target = "userEmail")
    EmployeeResponse toResponse(Employee employee);

    @Mapping(source = "teamId", target = "team.id")
    @Mapping(source = "professionId", target = "profession")
    @Mapping(source = "userId", target = "user")
    Employee toEntity(EmployeeCreateRequest request);

    @Mapping(source = "teamId", target = "team.id")
    @Mapping(source = "professionId", target = "profession")
    @Mapping(source = "userId", target = "user.id")
    void updateEntity(EmployeeUpdateRequest request, @MappingTarget Employee employee);

    @Mapping(source = "teamName", target = "team.teamName")
    @Mapping(source = "professionName", target = "profession")
    @Mapping(source = "userEmail", target = "user")
    Employee toEntity(EmployeeFilterRequest request);
}
