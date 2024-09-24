package com.company.toDoApp.service.v1.impl.auth;

import com.company.toDoApp.model.dto.Request.Filter.SignInRequest;
import com.company.toDoApp.security.UserPrincipal;
import com.company.toDoApp.security.jwt.JwtProvider;
import com.company.toDoApp.service.v1.impl.user.UserRegistrationService;
import com.company.toDoApp.service.v1.inter.auth.AuthenticationInterface;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationInterface {
    //AuthenticationManager interface-i login olan adamin teqdim etdiyi melumatlari yoxlayir.
    public final AuthenticationManager authenticationManager;
    public final JwtProvider jwtProvider;
    public final UserRegistrationService userRegistrationService;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserRegistrationService userRegistrationService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userRegistrationService = userRegistrationService;
    }
    @Override
    public String singInAndReturnJWT(SignInRequest request){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        UserPrincipal userPrincipal=(UserPrincipal) authentication.getPrincipal();
        String role = userRegistrationService.getRoleByEmail(userPrincipal.getUsername()); // mail-e gore istifadeci rollarini qaytarir (qeyd : getUserName bizim project-de mail qaytarir)
        userPrincipal = new UserPrincipal(userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getPassword(), role);

        return jwtProvider.generateToken(userPrincipal);

    }
    @Override
    public void signOut(HttpServletRequest request){
        if (jwtProvider.isTokenValid(request) && !jwtProvider.isTokenBlacklisted(jwtProvider.resolveToken(request))) {
            jwtProvider.tokenAddToBlackList(jwtProvider.resolveToken(request));
            System.out.println("Token etibarsızlaşdırıldı və blackliste əlavə edildi.");
        } else {
            System.out.println("Token artıq etibarsızdır və ya etibarsız bir token təqdim edildi.");
        }
        }
    }

