package com.company.toDoApp.service;

import com.company.toDoApp.dao.entity.Employee;
import com.company.toDoApp.service.client.UserVerificationClient;
import com.company.toDoApp.wrapper.UserEmployeeWrapper;
import org.springframework.stereotype.Service;

@Service
public class UserVerificationService {

    private final UserVerificationClient userVerificationClient;

    public UserVerificationService(UserVerificationClient userVerificationClient) {
        this.userVerificationClient = userVerificationClient;
    }

    public boolean verifyEmailExists(String email) {
        UserEmployeeWrapper[] users = userVerificationClient.getUsers();

        if (users != null) {
            for (UserEmployeeWrapper user : users) {
                if (email.equals(user.getUser().getUserEmail())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Employee getEmployeeByEmail(String email) {
        UserEmployeeWrapper[] users = userVerificationClient.getUsers();
        if(verifyEmailExists(email)){
            for (UserEmployeeWrapper user : users) {
                if (email.equals(user.getUser().getUserEmail())) {
                    return user.getEmployee();
                }
            }
        }
        return null;
    }
}
