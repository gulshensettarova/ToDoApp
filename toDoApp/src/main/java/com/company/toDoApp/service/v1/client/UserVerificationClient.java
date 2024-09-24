package com.company.toDoApp.service.v1.client;

import com.company.toDoApp.wrapper.UserEmployeeWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "userVerificationClient", url = "https://jsonplaceholder.typicode.com/users")
public interface UserVerificationClient {
    @GetMapping("/users")
    UserEmployeeWrapper[] getUsers();
}
