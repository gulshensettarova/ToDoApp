package com.company.toDoApp.service.v1.impl.user;

import com.company.toDoApp.model.dao.entity.Employee;
import com.company.toDoApp.model.dao.entity.User;
import com.company.toDoApp.model.dao.repository.EmployeeRepository;
import com.company.toDoApp.model.dao.repository.UserRepository;
import com.company.toDoApp.model.dto.Request.Create.UserCreateRequest;
import com.company.toDoApp.mapper.UserMapper;
import com.company.toDoApp.messaging.MessageSenderService;
import com.company.toDoApp.service.v1.inter.user.UserRegistrationInterface;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class UserRegistrationService implements UserRegistrationInterface {

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

    @Override
    @Transactional
    public boolean registerUser(UserCreateRequest request) {
        /// İstifadəçinin emailinin doğruluğunu yoxlayırıq
        if (userVerificationService.verifyEmailExists(request.getUserEmail())) {
            // Şifrəni hash edirik
            String hashedPassword = passwordEncoder.encode(request.getUserPassword());
            //Istifadecinin bazada olub-oolmadigini yoxlayiriq
            checkUserAlreadyExistOrNot(request.getUserEmail());
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
    @Override
    public User findByUserId(int userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User couldn't be found by following ID: "+userId));

    }
    @Override
    public User findByUserEmail(String email){
        return userRepository.findByUserEmail(email)
                .orElseThrow(()->new RuntimeException("User couldn't be found by following mail: "+email));
    }
    public void checkUserAlreadyExistOrNot(String email){
       Optional<User> user= userRepository.findByUserEmail(email);
        user.ifPresent(user1 -> {throw new RuntimeException("User Already Exist");});
    }
    @Override
    public String getRoleByEmail(String email) {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found by mail: " + email));
        return user.getUserStatus().getUserStatus(); // İstifadəçinin rolunu qaytarır
    }

}

