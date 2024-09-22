package com.company.toDoApp.controller.v1;

import com.company.toDoApp.model.dto.Request.Create.UserCreateRequest;
import com.company.toDoApp.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/registr")
public class UserRegistrController {

    private final UserRegistrationService userRegistrationService;

    public UserRegistrController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserCreateRequest request) {
        try {
            boolean isRegistered = userRegistrationService.registerUser(request);
            if (isRegistered) {
                return new ResponseEntity<>("User registered successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Email is already registered.", HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred during registration.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
