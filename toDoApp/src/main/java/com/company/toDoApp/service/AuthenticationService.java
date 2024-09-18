package com.company.toDoApp.service;

import com.company.toDoApp.dto.Request.Filter.SignInRequest;
import com.company.toDoApp.security.UserPrincipal;
import com.company.toDoApp.security.jwt.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    //AuthenticationManager interface-i login olan adamin teqdim etdiyi melumatlari yoxlayir.
    public final AuthenticationManager authenticationManager;
    public final JwtProvider jwtProvider;
    public final UserRegistrationService userRegistrationService;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserRegistrationService userRegistrationService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userRegistrationService = userRegistrationService;
    }

    public String singInAndReturnJWT(SignInRequest request){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        UserPrincipal userPrincipal=(UserPrincipal) authentication.getPrincipal();
        String role = userRegistrationService.getRoleByEmail(userPrincipal.getUsername()); // mail-e gore istifadeci rollarini qaytarir (qeyd : getUserName bizim project-de mail qaytarir)
        userPrincipal = new UserPrincipal(userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getPassword(), role);

        return jwtProvider.generateToken(userPrincipal);

    }
}
