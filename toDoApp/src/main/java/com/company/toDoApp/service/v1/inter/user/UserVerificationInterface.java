package com.company.toDoApp.service.v1.inter.user;

import com.company.toDoApp.model.dao.entity.Employee;

public interface UserVerificationInterface {
    Employee getEmployeeByEmail(String email);
    boolean verifyEmailExists(String email);
}
