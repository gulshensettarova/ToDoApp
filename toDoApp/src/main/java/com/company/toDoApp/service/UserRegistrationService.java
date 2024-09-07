package com.company.toDoApp.service;

import com.company.toDoApp.dao.entity.Employee;
import com.company.toDoApp.dao.entity.User;
import com.company.toDoApp.dao.repository.EmployeeRepository;
import com.company.toDoApp.dao.repository.UserRepository;
import com.company.toDoApp.dto.Request.Create.UserCreateRequest;
import com.company.toDoApp.mapper.UserMapper;
import com.company.toDoApp.messaging.MessageSenderService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final UserMapper userMapper;
    private final UserVerificationService userVerificationService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MessageSenderService messageSenderService;

    public UserRegistrationService(UserRepository userRepository,
                                   EmployeeRepository employeeRepository,
                                   UserVerificationService userVerificationService,
                                   UserMapper userMapper,
                                   BCryptPasswordEncoder passwordEncoder,
                                   MessageSenderService messageSenderService) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.userVerificationService = userVerificationService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.messageSenderService = messageSenderService;
    }


    @Transactional
    public boolean registerUser(UserCreateRequest request) {
        /// İstifadəçinin emailinin doğruluğunu yoxlayırıq
        if (userVerificationService.verifyEmailExists(request.getUserEmail())) {
            // Şifrəni hash edirik
            String hashedPassword = passwordEncoder.encode(request.getUserPassword());

            // User obyektini yaradıb, bazaya insert edirik
            User user = userMapper.toEntity(request);
            user.setUserPasswordHash(hashedPassword);
            userRepository.save(user);
            // Employee obyektini tapiriq
            Optional<Employee> employeeOptional = Optional.ofNullable(userVerificationService.getEmployeeByEmail(request.getUserEmail()));

            // Employee obyektini yoxlayiriq və bazaya insert edirik
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                employee.setUser(user);
                employeeRepository.save(employee);
            } else {
                Employee employee = new Employee();
                employee.setUser(user);
                employeeRepository.save(employee);
            }
            String message = "Your registration has been successfully completed!";
            messageSenderService.sendMessageToQueue(message,request.getUserEmail());
            return true;
        }
        return false;
    }
}

