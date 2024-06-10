package com.cine.login.adapters.controller;

import com.cine.login.adapters.dto.JwtAuthenticationResponse;
import com.cine.login.adapters.dto.LoginRequest;
import com.cine.login.adapters.dto.SignUpRequest;
import com.cine.login.domain.model.CinemaUser;
import com.cine.login.infrastructure.security.JwtTokenProvider;
import com.cine.login.usecase.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login attempt for user: " + loginRequest.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        System.out.println("Authentication successful for user: " + loginRequest.getEmail());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        System.out.println("JWT generated: " + jwt);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userService.findByEmail(signUpRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email is already taken!");
        }

        CinemaUser user = new CinemaUser();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setRole("ROLE_USER");
        user.setDni(signUpRequest.getDni());
        user.setAddress(signUpRequest.getAddress());
        user.setCity(signUpRequest.getCity());

        userService.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}