package com.company.toDoApp.security;

import com.company.toDoApp.dao.entity.User;
import com.company.toDoApp.dao.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Bu metod gonderilen parametre gore db-den istifadeci melumatlarin getirir
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
       User user= userRepository.findByUserEmail(mail)
               .orElseThrow(()->new RuntimeException("User not found"));
        return new UserPrincipal(user.getId(),user.getUserEmail(),user.getUserPasswordHash(),user.getUserStatus().getUserStatus());
    }
}
