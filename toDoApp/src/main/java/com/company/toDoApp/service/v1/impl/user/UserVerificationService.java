package com.company.toDoApp.service.v1.impl.user;

import com.company.toDoApp.model.dao.entity.Employee;
import com.company.toDoApp.service.v1.client.UserVerificationClient;
import com.company.toDoApp.service.v1.inter.user.UserVerificationInterface;
import com.company.toDoApp.wrapper.UserEmployeeWrapper;
import org.springframework.stereotype.Service;

@Service
public class UserVerificationService implements UserVerificationInterface {

    private final UserVerificationClient userVerificationClient;

    public UserVerificationService(UserVerificationClient userVerificationClient) {
        this.userVerificationClient = userVerificationClient;
    }
    @Override
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

    @Override
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
