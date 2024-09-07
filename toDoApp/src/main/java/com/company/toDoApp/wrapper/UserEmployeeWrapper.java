package com.company.toDoApp.wrapper;

import com.company.toDoApp.dao.entity.Employee;
import com.company.toDoApp.dao.entity.User;

public class UserEmployeeWrapper {
    private Employee employee;
    private User user;
    public UserEmployeeWrapper(Employee employee, User user) {
        this.employee = employee;
        this.user = user;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
