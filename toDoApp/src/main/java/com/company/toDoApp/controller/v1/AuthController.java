package com.company.toDoApp.controller.v1;

import com.company.toDoApp.model.dto.Request.Filter.SignInRequest;
import com.company.toDoApp.service.v1.impl.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest request){
        return  ResponseEntity.ok(authenticationService.singInAndReturnJWT(request));
    }
    @PostMapping("/logout")
    public ResponseEntity<String> signOut(HttpServletRequest request){
        authenticationService.signOut(request);
        return ResponseEntity.ok("Successfully signed out");

    }
}
